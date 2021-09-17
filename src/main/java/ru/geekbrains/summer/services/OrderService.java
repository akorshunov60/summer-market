package ru.geekbrains.summer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.summer.dto.OrderDto;
import ru.geekbrains.summer.dto.OrderItemDto;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.Order;
import ru.geekbrains.summer.model.OrderItem;
import ru.geekbrains.summer.model.ProductEntity;
import ru.geekbrains.summer.model.User;
import ru.geekbrains.summer.repositories.OrderRepository;
import ru.geekbrains.summer.utils.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final Cart cart;

    @Transactional
    public void createOrder(User user, String address, String phone) {
        Order order = new Order();
        order.setPrice(cart.getPrice());
        order.setItems(new ArrayList<>());
        order.setUser(user);
        order.setPhone(phone);
        order.setAddress(address);
        for (OrderItemDto o : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(o.getQuantity());
            ProductEntity productEntity = productService
                    .findById(o.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("ProductEntity not found"));
            orderItem.setPrice(productEntity.getPrice().multiply(BigDecimal.valueOf(o.getQuantity())));
            orderItem.setPricePerProduct(productEntity.getPrice());
            orderItem.setProductEntity(productEntity);
            order.getItems().add(orderItem);
        }
        orderRepository.save(order);
        cart.clear();
    }

    @Transactional
    public List<OrderDto> findAllDtosByUsername(String username) {
        return orderRepository.findAllByUsername(username).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}

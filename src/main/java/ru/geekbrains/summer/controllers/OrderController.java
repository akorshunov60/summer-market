package ru.geekbrains.summer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.summer.dto.OrderDto;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.exceptions.IncorrectFieldFillingException;
import ru.geekbrains.summer.model.User;
import ru.geekbrains.summer.services.OrderService;
import ru.geekbrains.summer.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public void createOrder(Principal principal, @RequestParam String address, @RequestParam String phone) {
        // создаем пустой массив для ошибок
        List<String> errors = new ArrayList<>();
        // проверка правильности заполнения поля address
        if(address.isBlank()) {
            errors.add("incorrect filling of the field 'address'");
        }
        // проверка правильности заполнения поля phone
        if(phone.isBlank()) {
            errors.add("incorrect filling of the field 'phone'");
        }

        if(!errors.isEmpty()) {
            throw new IncorrectFieldFillingException(errors);
        }
        User user = userService
                .findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Unable to create order. User not found"));
        orderService.createOrder(user, address, phone);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {

        return orderService.findOrderDtoById(id);
    }

    @GetMapping
    public List<OrderDto> getAllOrders(Principal principal) {

        return orderService.findAllDtosByUsername(principal.getName());
    }
}

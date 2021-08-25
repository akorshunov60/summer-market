package ru.geekbrains.summer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.summer.model.OrderItem;
import ru.geekbrains.summer.model.ProductEntity;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private BigDecimal pricePerProduct;
    private BigDecimal price;
    private int quantity;

    public OrderItemDto(ProductEntity productEntity) {
        this.productId = productEntity.getId();
        this.quantity = 1;
        this.pricePerProduct = productEntity.getPrice();
        this.price = productEntity.getPrice();
        this.productTitle = productEntity.getTitle();
    }

    public OrderItemDto(OrderItem orderItem) {
        this.productId = orderItem.getId();
        this.quantity = orderItem.getQuantity();
        this.pricePerProduct = orderItem.getPricePerProduct();
        this.price = orderItem.getPrice();
        this.productTitle = orderItem.getProductEntity().getTitle();
    }
    public void changeQuantity(int amount) {
        quantity += amount;
        price = pricePerProduct.multiply(BigDecimal.valueOf(quantity));
    }
}

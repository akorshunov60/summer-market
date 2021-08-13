package ru.geekbrains.summer.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.summer.market.model.Order;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private String address;
    private String phone;
    private BigDecimal price;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.address = order.getAddress();
        this.phone = order.getPhone();
        this.price = order.getPrice();
    }
}

package ru.geekbrains.summer.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.summer.market.dto.OrderDto;
import ru.geekbrains.summer.market.exceptions.IncorrectFieldFillingException;
import ru.geekbrains.summer.market.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.market.model.User;
import ru.geekbrains.summer.market.services.OrderService;
import ru.geekbrains.summer.market.services.UserService;

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
            errors.add("incorrect filling of the field address");
        }
        // проверка правильности заполнения поля phone
        if(phone.isBlank()) {
            errors.add("incorrect filling of the field phone");
        }

        if(!errors.isEmpty()) {
            throw new IncorrectFieldFillingException(errors);
        }
        User user = userService
                .findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Unable to create order. User not found"));;

        orderService.createOrder(user, address, phone);
    }

    @GetMapping
    public List<OrderDto> getAllOrders(Principal principal) {

        User user = userService
                .findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Unable to create order. User not found"));;
        return orderService.findAllDtosByUser(user);
    }
}

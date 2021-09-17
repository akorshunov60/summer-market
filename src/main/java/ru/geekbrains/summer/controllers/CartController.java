package ru.geekbrains.summer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.services.ProductService;
import ru.geekbrains.summer.beans.Cart;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final Cart cart;
    private final ProductService productService;

    @GetMapping
    public Cart getCart() {
        return cart;
    }

    @GetMapping("/add/{productId}")
    public void add(@PathVariable Long productId) {
        if (!cart.add(productId)) {
            cart.add(productService
                    .findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Unable add products to cart." +
                            "ProductEntity not found id: " + productId)));
        }
    }

    @GetMapping("/decrement/{productId}")
    public void decrement(@PathVariable Long productId) {
        cart.changeQuantity(productId);
    }

    @GetMapping("/remove/{productId}")
    public void remove(@PathVariable Long productId) {
        cart.remove(productId);
    }

    @GetMapping("/clear")
    public void clear() {
        cart.clear();
    }
}

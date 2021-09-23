package ru.geekbrains.summer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.summer.dto.StringResponse;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.services.CartService;
import ru.geekbrains.summer.services.ProductService;
import ru.geekbrains.summer.beans.Cart;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/{uuid}")
    public Cart getCart(Principal principal, @PathVariable String uuid) {
        return cartService.getCurrentCart(getCurrentCartSuffix(principal, uuid));
    }

    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCart());
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void add(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        Cart cart = cartService.getCurrentCart(getCurrentCartSuffix(principal, uuid));
        if (!cart.add(productId)) {
            cart.add(productService
                            .findById(productId)
                            .orElseThrow(
                                    () -> new ResourceNotFoundException(
                                            "Unable add product to cart. Product not found id: " + productId
                                    )));
        }
        cartService.updateCart(cart, getCurrentCartSuffix(principal, uuid));
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        Cart cart = cartService.getCurrentCart(getCurrentCartSuffix(principal, uuid));
        cart.changeQuantity(productId, -1);
        cartService.updateCart(cart, getCurrentCartSuffix(principal, uuid));
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        Cart cart = cartService.getCurrentCart(getCurrentCartSuffix(principal, uuid));
        cart.remove(productId);
        cartService.updateCart(cart, getCurrentCartSuffix(principal, uuid));
    }

    @GetMapping("/{uuid}/clear")
    public void clear(Principal principal, @PathVariable String uuid) {
        Cart cart = cartService.getCurrentCart(getCurrentCartSuffix(principal, uuid));
        cart.clear();
        cartService.updateCart(cart, getCurrentCartSuffix(principal, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void merge(Principal principal, @PathVariable String uuid) {
        Cart guestCart = cartService.getCurrentCart(uuid);
        Cart userCart = cartService.getCurrentCart(principal.getName());
        userCart.merge(guestCart);
        cartService.updateCart(userCart, principal.getName());
        cartService.deleteCart(uuid);
    }

    private String getCurrentCartSuffix(Principal principal, String uuid) {
        if (principal != null) {
            return principal.getName();
        }
        return uuid;
    }
}

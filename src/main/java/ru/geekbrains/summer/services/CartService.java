package ru.geekbrains.summer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.summer.beans.Cart;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.ProductEntity;

@Service
@RequiredArgsConstructor
public class CartService {

    private final String CART_PREFIX = "product_cart_";

    private final ProductService productService;

    private final RedisTemplate<String, Object> redisTemplate;

    public void addToCart(String cartId, Long productId) {
        Cart cart = getCurrentCart(cartId);
        if (cart.add(productId)) {
            save(cartId, cart);
            return;
        }
        ProductEntity productEntity = productService
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " is missed. (Add to cart)"));
        cart.add(productEntity);
        save(cartId, cart);
    }

    public void changeQuantityProduct(String cartId, Long productId) {
        Cart cart = getCurrentCart(cartId);
        cart.changeQuantity(productId);
        save(cartId, cart);
    }

    public void removeProduct(String cartId, Long productId) {
        Cart cart = getCurrentCart(cartId);
        cart.remove(productId);
        save(cartId, cart);
    }

    public boolean isCartExists(String cartId) throws NullPointerException {
            return redisTemplate.hasKey(CART_PREFIX + cartId);
    }

    public Cart getCurrentCart(String cartId) throws NullPointerException {
        if (!redisTemplate.hasKey(CART_PREFIX + cartId)) {
            redisTemplate.opsForValue().set(CART_PREFIX + cartId, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(CART_PREFIX + cartId);
    }

    public void save(String cartId, Cart cart) {
        redisTemplate.opsForValue().set(CART_PREFIX + cartId, cart);
    }

    public void clear(String cartId) {
        Cart cart = getCurrentCart(cartId);
        cart.clear();
        save(cartId, cart);
    }

    public void merge(String userCartId, String guestCartId) {
        Cart userCart = getCurrentCart(userCartId);
        Cart guestCart = getCurrentCart(guestCartId);
        userCart.merge(guestCart);
        save(userCartId, userCart);
        save(guestCartId, guestCart);
    }
}

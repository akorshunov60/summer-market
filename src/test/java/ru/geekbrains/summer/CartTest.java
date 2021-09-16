package ru.geekbrains.summer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.summer.model.CategoryEntity;
import ru.geekbrains.summer.model.ProductEntity;
import ru.geekbrains.summer.services.CartService;
import ru.geekbrains.summer.services.ProductService;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class CartTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private ProductService productService;

//    @BeforeEach
//    public void initCart() {
//        cartService.clear("test_cart");
//    }

    @Test
    public void addToCartTest() {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(5L);
        productEntity.setTitle("XL");
        productEntity.setPrice(BigDecimal.valueOf(100.0));

        CategoryEntity ctge = new CategoryEntity();
        ctge.setTitle("XXL");
        productEntity.setCategoryEntity(ctge);

        Mockito.doReturn(Optional.of(productEntity)).when(productService).findById(5L);
        cartService.addToCart("test_cart", 5L);
        cartService.addToCart("test_cart", 5L);
        cartService.addToCart("test_cart", 5L);
        Mockito.verify(productService, Mockito.times(1)).findById(ArgumentMatchers.eq(5L));
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
    }
}

package ru.geekbrains.summer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.ProductEntity;
import ru.geekbrains.summer.repositories.ProductRepository;
import ru.geekbrains.summer.services.ProductService;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void findOneProductTest() {

        ProductEntity productFromDB = new ProductEntity();
        productFromDB.setId(2L);
        productFromDB.setTitle("Bread");
        productFromDB.setPrice(BigDecimal.valueOf(28));

        Mockito.doReturn(Optional.of(productFromDB))
                .when(productRepository)
                .findById(2L);
        ProductEntity testProduct = productService
                .findById(2L)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Assertions.assertNotNull(testProduct);
        Assertions.assertEquals("Bread", testProduct.getTitle());
        Mockito.verify(productRepository, Mockito
                .times(1))
                .findById(ArgumentMatchers.eq(2L));
    }
}

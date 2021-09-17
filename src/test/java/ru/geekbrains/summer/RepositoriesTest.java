package ru.geekbrains.summer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.summer.model.ProductEntity;
import ru.geekbrains.summer.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class RepositoriesTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void goProductRepositoryTest() {
        ProductEntity pe = new ProductEntity();
        pe.setTitle("Coffee");
        pe.setPrice(BigDecimal.valueOf(480));
        entityManager.persist(pe);
        entityManager.flush();

        List<ProductEntity> products = productRepository.findAll();

        Assertions.assertEquals(4, products.size());
        Assertions.assertEquals("Coffee", products.get(3).getTitle());
    }

    @Test
    public void initDbTest() {
        List<ProductEntity> products = productRepository.findAll();
        Assertions.assertEquals(3, products.size());
    }
}

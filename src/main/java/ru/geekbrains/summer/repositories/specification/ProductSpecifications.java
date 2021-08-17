package ru.geekbrains.summer.repositories.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.summer.model.Product;

import java.math.BigDecimal;

public class ProductSpecifications {

    public static Specification<Product> priceGreaterOrEqualThan(BigDecimal minPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> priceLessThanOrEqualTo(BigDecimal maxPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> titleLike(String title) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("title"), "%" + title + "%");
    }
}

package ru.geekbrains.summer.repositories.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.summer.model.ProductEntity;

import java.math.BigDecimal;

public class ProductSpecifications {

    public static Specification<ProductEntity> priceGreaterOrEqualThan(BigDecimal minPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<ProductEntity> priceLessThanOrEqualTo(BigDecimal maxPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<ProductEntity> titleLike(String title) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("title"), "%" + title + "%");
    }
}

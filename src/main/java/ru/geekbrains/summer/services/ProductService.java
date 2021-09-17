package ru.geekbrains.summer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.summer.model.Product;
import ru.geekbrains.summer.repositories.ProductRepository;
import ru.geekbrains.summer.repositories.specification.ProductSpecifications;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Specification<Product> buildSpecification(BigDecimal minPrice, BigDecimal maxPrice, String title) {

        Specification<Product> specification = Specification.where(null);

        if(minPrice != null) {
            specification = specification.and(ProductSpecifications.priceGreaterOrEqualThan(minPrice));
        }
        if(maxPrice != null) {
            specification = specification.and(ProductSpecifications.priceLessThanOrEqualTo(maxPrice));
        }
        if(title != null) {
            specification = specification.and(ProductSpecifications.titleLike(title));
        }
        return specification;
    }

    public Page<Product> findPage(int pageIndex, int pageSize, Specification<Product> spec) {
        return productRepository.findAll(spec, PageRequest.of(pageIndex, pageSize));
    }

    public Product save(Product newProduct) {
        return productRepository.save(newProduct);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}

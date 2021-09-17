package ru.geekbrains.summer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.ProductEntity;
import ru.geekbrains.summer.repositories.ProductRepository;
import ru.geekbrains.summer.repositories.specification.ProductSpecifications;
import ru.geekbrains.summer.soap.products.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    public Specification<ProductEntity> buildSpecification(BigDecimal minPrice, BigDecimal maxPrice, String title) {

        Specification<ProductEntity> specification = Specification.where(null);

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

    public Page<ProductEntity> findPage(int pageIndex, int pageSize, Specification<ProductEntity> spec) {
        return productRepository.findAll(spec, PageRequest.of(pageIndex, pageSize));
    }

    public ProductEntity save(ProductEntity newProductEntity) {
        return productRepository.save(newProductEntity);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public static final Function<ProductEntity, Product> functionEntityToSoap = pre -> {
        Product pr = new Product();
        pr.setId(pre.getId());
        pr.setTitle(pre.getTitle());
        pr.setCategoryTitle(pre.getCategoryEntity().getTitle());
        pr.setPrice(pre.getPrice());
        return pr;
    };

    public List<Product> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(functionEntityToSoap)
                .collect(Collectors.toList());
    }

    public Product getById(long id) {
        return productRepository
                .findById(id)
                .map(functionEntityToSoap)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
    }
}

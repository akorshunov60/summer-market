package ru.geekbrains.summer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.ProductEntity;
import ru.geekbrains.summer.repositories.ProductRepository;
import ru.geekbrains.summer.soap.products.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String FILTER_MIN_PRICE = "min_price";
    private static final String FILTER_MAX_PRICE = "max_price";
    private static final String FILTER_TITLE = "title";

    private final ProductRepository productRepository;

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    private Specification<ProductEntity> buildSpecification(MultiValueMap<String, String> params) {

        Specification<ProductEntity> specification = Specification.where(null);

        if (params.containsKey(FILTER_MIN_PRICE) && !Objects.requireNonNull(params.getFirst(FILTER_MIN_PRICE)).isBlank()) {
            BigDecimal minPrice = BigDecimal.valueOf(Double.parseDouble(Objects.requireNonNull(params.getFirst(FILTER_MIN_PRICE))));
            specification = specification.and(ProductSpecifications.priceGreaterOrEqualThan(minPrice));
        }
        if (params.containsKey(FILTER_MAX_PRICE) && !Objects.requireNonNull(params.getFirst(FILTER_MAX_PRICE)).isBlank()) {
            BigDecimal maxPrice = BigDecimal.valueOf(Double.parseDouble(Objects.requireNonNull(params.getFirst(FILTER_MAX_PRICE))));
            specification = specification.and(ProductSpecifications.priceLessThanOrEqualTo(maxPrice));
        }
        if (params.containsKey(FILTER_TITLE) && !Objects.requireNonNull(params.getFirst(FILTER_TITLE)).isBlank()) {
            String title = params.getFirst(FILTER_TITLE);
            specification = specification.and(ProductSpecifications.titleLike(title));
        }
        return specification;
    }

    public Page<ProductEntity> findPage(int pageIndex, int pageSize, MultiValueMap<String, String> params) {
        return productRepository.findAll(buildSpecification(params), PageRequest.of(pageIndex, pageSize));
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

    private static class ProductSpecifications {

        private static Specification<ProductEntity> priceGreaterOrEqualThan(BigDecimal minPrice) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .greaterThanOrEqualTo(root.get("price"), minPrice);
        }

        private static Specification<ProductEntity> priceLessThanOrEqualTo(BigDecimal maxPrice) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .lessThanOrEqualTo(root.get("price"), maxPrice);
        }

        private static Specification<ProductEntity> titleLike(String title) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .like(root.get("title"), "%" + title + "%");
        }
    }
}

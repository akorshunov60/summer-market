package ru.geekbrains.summer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.summer.dto.ProductDto;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.ProductEntity;
import ru.geekbrains.summer.services.ProductService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{id}")
    public ProductDto findById(@PathVariable Long id) {
        ProductEntity p = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ProductEntity not found, id: " + id));
        return new ProductDto(p);
    }

    @GetMapping
    public Page<ProductDto> findAll(@RequestParam(name = "p", defaultValue = "1") int pageIndex,
                                    @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
                                    @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
                                    @RequestParam(name = "title", required = false) String title) {

        return productService.findPage(pageIndex - 1, 5,
                productService.buildSpecification(minPrice, maxPrice, title)).map(ProductDto::new);
    }

    @PostMapping
    public ProductDto createNewProduct(@RequestBody ProductDto newProductDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setPrice(newProductDto.getPrice());
        productEntity.setTitle(newProductDto.getTitle());
        return new ProductDto(productService.save(productEntity));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}

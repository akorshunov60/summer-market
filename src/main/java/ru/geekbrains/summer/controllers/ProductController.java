package ru.geekbrains.summer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.summer.dto.ProductDto;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.Product;
import ru.geekbrains.summer.services.ProductService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{id}")
    public ProductDto findById(@PathVariable Long id) {
        Product p = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
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
        Product product = new Product();
        product.setPrice(newProductDto.getPrice());
        product.setTitle(newProductDto.getTitle());
        return new ProductDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}

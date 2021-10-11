package ru.geekbrains.summer.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.summer.dto.ProductDto;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.ProductEntity;
import ru.geekbrains.summer.services.ProductService;

import java.util.Map;

@RequiredArgsConstructor
@Api("Set of endpoints for products")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @ApiOperation("Returns a specific product by their identifier. 404 if does not exist.")
    @GetMapping(value = "/{id}")
    public ProductDto findById(@ApiParam("Id of the product. Cannot be empty.") @PathVariable Long id) {
        ProductEntity p = productService
                .findById(id).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "ProductEntity not found, id: " + id
                        ));
        return new ProductDto(p);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title", example = "Bread", type = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "min_price", example = "100.0", type = "BigDecimal", required = false, paramType = "query"),
            @ApiImplicitParam(name = "max_price", example = "2000.0", type = "BigDecimal", required = false, paramType = "query")
    })
    @ApiOperation("Returns list of all products in the store.")
    @GetMapping
    public Page<ProductDto> findAll(
            @RequestParam(name = "p", defaultValue = "1") int pageIndex,
            @RequestParam MultiValueMap<String, String> params
    ) {
        return productService.findPage(pageIndex - 1, 5, params).map(ProductDto::new);
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

    @PostMapping("/pack")
    public void demoPack(@RequestParam Map<String, String> params) {
        System.out.println(1);
    }
}

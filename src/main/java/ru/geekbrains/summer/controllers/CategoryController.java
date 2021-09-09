package ru.geekbrains.summer.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.summer.model.CategoryEntity;
import ru.geekbrains.summer.services.CategoryService;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Slf4j
@Api("Set of endpoints for categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryEntity findById(@PathVariable Long id) {
        return categoryService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryEntity not found, id: " + id));
    }

    @GetMapping
    @ApiOperation("Returns list of all categories in the repository.")
    public List<CategoryEntity> findAllCategories() {
        return categoryService.findAll();
    }
}

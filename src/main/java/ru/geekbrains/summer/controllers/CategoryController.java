package ru.geekbrains.summer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.CategoryEntity;
import ru.geekbrains.summer.services.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryEntity findById(@PathVariable Long id) {
        return categoryService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryEntity not found, id: " + id));
    }

    @GetMapping
    public List<CategoryEntity> findAllCategories() {
        return categoryService.findAll();
    }
}

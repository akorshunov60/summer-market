package ru.geekbrains.summer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.summer.model.CategoryEntity;
import ru.geekbrains.summer.services.CategoryService;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryEntity findById(@PathVariable Long id) {
        CategoryEntity c = categoryService.findById(id).orElseThrow(() -> new ResourceNotFoundException("CategoryEntity not found, id: " + id));
        return c;
    }
}

package ru.geekbrains.summer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.model.CategoryEntity;
import ru.geekbrains.summer.repositories.CategoryRepository;
import ru.geekbrains.summer.soap.categories.Category;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Optional<CategoryEntity> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public static final Function<CategoryEntity, Category> functionEntityToSoap = ctge -> {
        Category ctg = new Category();
        ctg.setTitle(ctg.getTitle());
        ctge.getProducts().stream().map(ProductService.functionEntityToSoap).forEach(pr -> ctg.getProducts().add(pr));
        return ctg;
    };

    public Category getByTitle(String title) {
        return categoryRepository
                .findByTitle(title)
                .map(functionEntityToSoap)
                .orElseThrow(() -> new ResourceNotFoundException("Category " + title + " not found"));
    }

}

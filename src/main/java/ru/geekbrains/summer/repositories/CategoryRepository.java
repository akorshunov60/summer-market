package ru.geekbrains.summer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.summer.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

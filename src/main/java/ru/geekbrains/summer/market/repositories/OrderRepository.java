package ru.geekbrains.summer.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.summer.market.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

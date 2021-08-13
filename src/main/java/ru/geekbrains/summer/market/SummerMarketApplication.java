package ru.geekbrains.summer.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SummerMarketApplication {
	// Домашнее задание:
	// ----
	// 1. На странице заказов отобразить список и количество продуктов в заказе
	// 2. Ругаться с backend'а если поля адрес или телефон заказа не заполнены

	public static void main(String[] args) {
		SpringApplication.run(SummerMarketApplication.class, args);
	}
}

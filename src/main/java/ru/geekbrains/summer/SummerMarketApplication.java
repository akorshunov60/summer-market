package ru.geekbrains.summer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:security.properties")
@SpringBootApplication
public class SummerMarketApplication {
/*
	Домашнее задание:
	1. Разобраться с логикой работы корзина + redis
	2. Предложить в виде кода или текста варианты улучшения/упрощения логики взаимодействия с кешированной корзиной

	План по магазину:
	+ 1. Привязка корзины к пользователю, подключение Reddis
	2. Рассылка писем
	3. Админка
	4. Swagger
	+ 5. Фильтры по товарам
	6. Маппинг DTO <-> Entity, @EntityGraph
	7. Картинки
	8. Промо-коды
	9. Регистрация пользователя
 */

	public static void main(String[] args) {
		SpringApplication.run(SummerMarketApplication.class, args);
	}
}

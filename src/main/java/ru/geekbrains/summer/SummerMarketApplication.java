package ru.geekbrains.summer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

// Параметр VM Options для выбора профиля: -Dspring.profiles.active=dev (для версии Intellij IDEA не Ultimate)

@PropertySource("classpath:security.properties")
@SpringBootApplication
public class SummerMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SummerMarketApplication.class, args);
	}
}

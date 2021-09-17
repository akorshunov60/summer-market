package ru.geekbrains.summer.market.exceptions;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для обработки exception при неправильном заполнении полей формы запроса
 */

public class IncorrectFieldFillingException extends RuntimeException{

    private List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public IncorrectFieldFillingException(List<String> messages) {
        super(String.join(",", messages));
//        super(messages.stream().collect(Collectors.joining(",")));
    }

    public IncorrectFieldFillingException(String message) {
        this(List.of(message));
    }
}

package ru.geekbrains.summer.exceptions;

import java.util.List;

/**
 * Класс для обработки exception при неправильном заполнении полей формы запроса
 */

public class IncorrectFieldFillingException extends RuntimeException{

    private List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public IncorrectFieldFillingException(List<String> messages) {
//      super(messages.stream().collect(Collectors.joining(",")));
        super(String.join(",", messages));
        this.messages = messages;
    }

    public IncorrectFieldFillingException(String message) {
        this(List.of(message));
    }
}

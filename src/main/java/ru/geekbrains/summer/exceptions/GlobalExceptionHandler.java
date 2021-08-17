package ru.geekbrains.summer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new MarketError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Метод перехватывает exception при неправильном заполнении полей формы запроса
      * @param e
     * @return массив с указанием пустых или неправильно заполненных полей формы запроса
     */
    @ExceptionHandler
    public ResponseEntity<?> catchIncorrectFieldFillingException(IncorrectFieldFillingException e) {
        return new ResponseEntity<>(new MarketError(e.getMessages()), HttpStatus.BAD_REQUEST);
    }
}

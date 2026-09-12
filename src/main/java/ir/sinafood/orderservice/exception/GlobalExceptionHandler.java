package ir.sinafood.orderservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderCreationException.class)
    public ResponseEntity<?> failedOrderCreation(OrderCreationException ex) {
        log.error("Order creation failed", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", ex.getMessage()
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        log.error("user not found", ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", ex.getMessage()
                ));
    }
}

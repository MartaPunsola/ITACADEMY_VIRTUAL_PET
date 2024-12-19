package cat.itacademy.s05.t02.VirtualPet.exception;

import cat.itacademy.s05.t02.VirtualPet.exception.custom.NameAlreadyExistsException;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NoPetsException;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NoUsersException;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.PetNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //fer servir logger???
    //es pot fer un mètode de creació de error response per no repetir codi

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException e) {
        log.error("Error: {}", e.getMessage()); //????
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(PetNotFoundException e) {
        log.error("Error: {}", e.getMessage()); //????
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(NameAlreadyExistsException e) {
        log.error("Error: {}", e.getMessage()); //????
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(NoPetsException.class)
    public ResponseEntity<ErrorResponse> handleException(NoPetsException e) {
        log.error("Error: {}", e.getMessage()); //????
        ErrorResponse error = new ErrorResponse(HttpStatus.NO_CONTENT.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error);
    }

    @ExceptionHandler(NoUsersException.class)
    public ResponseEntity<ErrorResponse> handleException(NoUsersException e) {
        log.error("Error: {}", e.getMessage()); //????
        ErrorResponse error = new ErrorResponse(HttpStatus.NO_CONTENT.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        log.error("Error: {}", e.getMessage()); //????
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("Error: {}", e.getMessage()); //????
        // Crear un mapa d'errors amb els camps i missatges
        Map<String, String> fieldErrors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );
        /*Map<String, String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));*/

        // Crear un objecte ErrorResponse personalitzat
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed for one or more fields.",
                System.currentTimeMillis(),
                fieldErrors // Incloure el mapa d'errors específics
        );

        // Retornar l'error com a ResponseEntity
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.error("Data integrity violation: {}", e.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Data integrity error: possible duplicate or invalid data.",
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    } //aquest crec que no es crida mai

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Constraint violation: {}", e.getMessage());

        // Crear un mapa amb els errors de camps
        Map<String, String> fieldErrors = new HashMap<>();
        e.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            fieldErrors.put(field, message);
        });

        // Crear la resposta d'error
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed for one or more fields.",
                System.currentTimeMillis(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } //aquest mètode funciona correctament



    //errors generals
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage()); //redundant???
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

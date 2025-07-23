package ShopTech.ShopTechEpicode.exception;

import ShopTech.ShopTechEpicode.dto.AuthResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Gestione validazioni fallite (es. @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    // Gestione eccezioni personalizzate (es. risorse non trovate)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AuthResponseDto> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new AuthResponseDto(ex.getMessage(), null)
        );
    }

    // Fallback per errori generici
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponseDto> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new AuthResponseDto("Errore interno: " + ex.getMessage(), null)
        );
    }
}

package med.voll.api.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", "ERR_DUPLICATE_RECORD");
        errorResponse.put("message", "El médico ya está registrado con este correo o documento.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse); // Código 409
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        if ("ERR_DUPLICATE_RECORD".equals(ex.getMessage())) {
            Map<String, String> conflictResponse = new HashMap<>();
            conflictResponse.put("code", "ERR_DUPLICATE_RECORD");
            conflictResponse.put("message", "El médico ya está registrado con este correo o documento.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse); // Código 409
        }

        // Manejo genérico para otros casos
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", "ERR_UNKNOWN");
        errorResponse.put("message", "Ha ocurrido un error inesperado.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getMessage().contains("Enum")) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("code", "ERR_INVALID_SPECIALITY");
            errorResponse.put("message", "La especialidad ingresada no es válida.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse); // Código 400
        }

        // Manejo genérico para otros casos de HttpMessageNotReadableException
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", "ERR_INVALID_REQUEST");
        errorResponse.put("message", "Solicitud inválida.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse); // Código 400
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        // Crear un mapa para almacenar los errores
        Map<String, String> errorResponse = new HashMap<>();

        // Iterar sobre los errores de validación
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorResponse.put("field", error.getField());
            errorResponse.put("message", error.getDefaultMessage());
        });

        errorResponse.put("code", "ERR_VALIDATION_FAILED");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", "ERR_MEDICO_NOT_FOUND");
        errorResponse.put("message", "El recurso solicitado no fue encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse); // Código 404
    }
}
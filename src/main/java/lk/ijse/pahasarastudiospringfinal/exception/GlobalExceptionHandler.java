package lk.ijse.pahasarastudiospringfinal.exception;

import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // This catches all Backend/Runtime errors and returns JSON
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO> handleRuntimeException(RuntimeException ex) {
        ResponseDTO response = new ResponseDTO();
        response.setCode("05");
        response.setMessage("Backend Error: " + ex.getMessage());
        response.setContent(null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handles any other general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGeneralException(Exception ex) {
        ResponseDTO response = new ResponseDTO();
        response.setCode("06");
        response.setMessage("System Error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
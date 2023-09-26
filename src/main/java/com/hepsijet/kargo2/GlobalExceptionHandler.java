/*package com.hepsijet.kargo2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        // Erişim reddedildiğinde HTTP 403 Forbidden yanıtı ile özel hata mesajını döndürün
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erişim reddedildi. Yetkiniz yok.");
    }

    // Diğer hata türlerini ele almak için gerekirse yeni @ExceptionHandler metotları ekleyebilirsiniz
}

 */


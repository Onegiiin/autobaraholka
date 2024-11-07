package by.autobaraholka.userMicroservice.controller;

import by.autobaraholka.userMicroservice.model.PasswordResetConfirmRequest;
import by.autobaraholka.userMicroservice.model.PasswordResetRequest;
import by.autobaraholka.userMicroservice.service.AuthenticationService;
import by.autobaraholka.userMicroservice.service.ResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Восстановление пароля")
public class PasswordResetController {

    private final ResetService resetService;

    @Operation(summary = "Запрос на восстановление пароля")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid PasswordResetRequest request) {
        resetService.sendPasswordResetLink(request.getEmail());
        return ResponseEntity.ok("Ссылка для восстановления пароля отправлена на вашу почту.");
    }

    @Operation(summary = "Запрос на смену пароля")
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetConfirmRequest request) {
        return resetService.resetPassword(request.getToken(), request.getNewPassword());
    }

    @Operation(summary = "Открытие формы для сброса пароля или сброс пароля")
    @GetMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token) {
        return resetService.isTokenValid(token) ? ResponseEntity.ok("Придумайте новый пароль") : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Недействительная или истекшая ссылка для восстановления пароля.");
    }
}

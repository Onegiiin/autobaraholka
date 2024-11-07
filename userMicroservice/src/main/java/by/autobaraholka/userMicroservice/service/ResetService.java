package by.autobaraholka.userMicroservice.service;

import by.autobaraholka.userMicroservice.model.PasswordResetToken;
import by.autobaraholka.userMicroservice.model.User;
import by.autobaraholka.userMicroservice.repository.PasswordResetTokenRepository;
import by.autobaraholka.userMicroservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final DefaultEmailService emailService;

    public void sendPasswordResetLink(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким email не найден"));

        PasswordResetToken resetToken = null;
        resetToken = tokenRepository.findByUser_Email(email).orElse(null);
        if (resetToken != null && resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            resetToken = null;
        }
        if (resetToken == null){
            String token = UUID.randomUUID().toString();
            resetToken = PasswordResetToken.builder()
                    .token(token)
                    .expiryDate(LocalDateTime.now().plusHours(1))
                    .user(user)
                    .build();
            tokenRepository.save(resetToken);
        }

        String resetLink = "http://localhost:8081/reset-password?token=" + resetToken.getToken();
        String subject = "Сброс пароля";
        String message = "Для сброса пароля перейдите по следующей ссылке: " + resetLink;
        emailService.sendSimpleEmail(user.getEmail(), subject, message);

    }

    public boolean isTokenValid(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token).orElse(null);
        return resetToken != null && !resetToken.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public ResponseEntity<String> resetPassword(String token, String newPassword) {
        // Проверка действительности токена
        if (!isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Недействительная или истекшая ссылка для восстановления пароля.");
        }

        // Проверка наличия токена в базе данных
        PasswordResetToken resetToken = tokenRepository.findByToken(token).orElse(null);
        if (resetToken == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Токен не найден.");
        }

        User user = resetToken.getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Пользователь с таким токеном не найден.");
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            tokenRepository.delete(resetToken);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Пароль успешно изменен.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Новый пароль не передан.");
        }
    }

}

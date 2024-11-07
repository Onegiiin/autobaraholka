package by.autobaraholka.userMicroservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на сброс пароля")
public class PasswordResetConfirmRequest {

    @Schema(description = "Токен для сброса пароля", example = "reset-token-123")
    @NotBlank(message = "Токен не может быть пустым")
    private String token;

    @Schema(description = "Новый пароль", example = "my_new_password")
    @Size(min = 5, max = 255, message = "Пароль должен содержать от 5 до 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String newPassword;
}

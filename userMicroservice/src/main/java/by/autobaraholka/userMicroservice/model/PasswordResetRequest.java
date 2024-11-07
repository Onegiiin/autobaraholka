package by.autobaraholka.userMicroservice.model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на восстановление пароля")
public class PasswordResetRequest {

    @Schema(description = "Электронная почта пользователя", example = "user@example.com")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    private String email;
}

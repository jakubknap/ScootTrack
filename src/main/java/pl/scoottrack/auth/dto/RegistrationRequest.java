package pl.scoottrack.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Reprezentuje żądanie rejestracji nowego użytkownika")
public class RegistrationRequest {

    @NotEmpty(message = "Imie jest wymagane")
    @NotBlank(message = "Imie jest wymagane")
    @Schema(description = "Imię użytkownika", example = "Jan", required = true)
    private String firstname;

    @Email(message = "Format e-maila jest niepoprawny")
    @NotEmpty(message = "Email jest wymagany")
    @NotBlank(message = "Email jest wymagany")
    @Schema(description = "Adres e-mail użytkownika", example = "jan.kowalski@example.com", required = true)
    private String email;

    @Size(min = 8, message = "Hasło powinno mieć minimum 8 znaków")
    @NotEmpty(message = "Hasło jest wymagane")
    @NotBlank(message = "Hasło jest wymagane")
    @Schema(description = "Hasło użytkownika", example = "securePassword123", required = true)
    private String password;
}
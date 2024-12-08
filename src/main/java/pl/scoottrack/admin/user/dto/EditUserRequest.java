package pl.scoottrack.admin.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Schema(description = "Reprezentuje żądanie edycji danych użytkownika")
public record EditUserRequest(

        @NotNull(message = "Uuid użytkownika jest wymagany")
        @Schema(description = "UUID użytkownika", example = "123e4567-e89b-12d3-a456-426614174001", required = true)
        UUID uuid,

        @NotEmpty(message = "Imie jest wymagane")
        @NotBlank(message = "Imie jest wymagane")
        @Schema(description = "Imię użytkownika", example = "Jan", required = true)
        String firstname,

        @Email(message = "Format e-maila jest niepoprawny")
        @NotEmpty(message = "Email jest wymagany")
        @NotBlank(message = "Email jest wymagany")
        @Schema(description = "Adres e-mail użytkownika", example = "jan.kowalski@example.com", required = true)
        String email,

        @NotNull(message = "Status konta jest wymagany")
        @Schema(description = "Status konta: czy jest zablokowane", example = "false", required = true)
        Boolean accountLocked,

        @NotNull(message = "Status aktywacji konta jest wymagany")
        @Schema(description = "Status konta: czy jest aktywne", example = "true", required = true)
        Boolean enabled,

        @NotEmpty(message = "Role użytkownika nie mogą być puste")
        @NotNull(message = "Role użytkownika są wymagane")
        @Schema(description = "Lista ról przypisanych do użytkownika", example = "[\"ADMIN\", \"USER\"]", required = true)
        List<String> roles
) {}
package pl.scoottrack.admin.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record EditUserRequest(

        @NotNull(message = "Uuid użytkownika jest wymagany")
        UUID uuid,

        @NotEmpty(message = "Imie jest wymagane")
        @NotBlank(message = "Imie jest wymagane")
        String firstname,

        @Email(message = "Format e-maila jest niepoprawny")
        @NotEmpty(message = "Email jest wymagany")
        @NotBlank(message = "Email jest wymagany")
        String email,

        @NotNull(message = "Status konta jest wymagany")
        Boolean accountLocked,

        @NotNull(message = "Status aktywacji konta jest wymagany")
        Boolean enabled,

        @NotEmpty(message = "Role użytkownika nie mogą być puste")
        @NotNull(message = "Role użytkownika są wymagane")
        List<String> roles
) {}

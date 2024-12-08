package pl.scoottrack.admin.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Reprezentuje odpowiedź z listą użytkowników")
public record UsersListResponse(

        @Schema(description = "UUID użytkownika", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID uuid,

        @Schema(description = "Adres e-mail użytkownika", example = "jan.kowalski@example.com")
        String email,

        @Schema(description = "Imię użytkownika", example = "Jan")
        String firstname,

        @Schema(description = "Status aktywacji konta", example = "true")
        boolean enabled,

        @Schema(description = "Status blokady konta", example = "false")
        boolean locked
) {}
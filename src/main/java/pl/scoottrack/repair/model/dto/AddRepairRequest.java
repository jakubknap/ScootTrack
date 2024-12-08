package pl.scoottrack.repair.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Reprezentuje żądanie dodania naprawy hulajnogi")
public record AddRepairRequest(

        @NotNull(message = "Uuid hulajnogi jest wymagany")
        @Schema(description = "UUID hulajnogi", required = true)
        UUID scooterUuid,

        @NotNull(message = "Tytuł naprawy jest wymagany")
        @NotEmpty(message = "Tytuł naprawy nie może być pusty")
        @Schema(description = "Tytuł naprawy", example = "Wymiana kół", required = true)
        String title,

        @NotNull(message = "Opis naprawy jest wymagany")
        @NotEmpty(message = "Opis naprawy nie może być pusty")
        @Schema(description = "Opis naprawy", example = "Koła zostały wymienione na nowe", required = true)
        String description,

        @Schema(description = "Cena naprawy", example = "250.00")
        BigDecimal price
) {}
package pl.scoottrack.repair.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Reprezentuje żądanie edycji naprawy hulajnogi")
public record EditRepairRequest(

        @NotNull(message = "Uuid naprawy jest wymagany")
        @Schema(description = "UUID naprawy", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
        UUID repairUuid,

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
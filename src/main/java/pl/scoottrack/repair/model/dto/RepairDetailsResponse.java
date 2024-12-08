package pl.scoottrack.repair.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Reprezentuje szczegóły naprawy hulajnogi")
public record RepairDetailsResponse(
        @Schema(description = "Tytuł naprawy", example = "Wymiana kół")
        String title,

        @Schema(description = "Opis naprawy", example = "Koła zostały wymienione na nowe")
        String description,

        @Schema(description = "Cena naprawy", example = "250.00")
        BigDecimal price,

        @Schema(description = "Data utworzenia naprawy", example = "2023-12-01T15:30:00")
        LocalDateTime createdDate,

        @Schema(description = "UUID hulajnogi", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID scooterUuid,

        @Schema(description = "UUID naprawy", example = "987e4567-e89b-12d3-a456-426614174000")
        UUID repairUuid
) {}
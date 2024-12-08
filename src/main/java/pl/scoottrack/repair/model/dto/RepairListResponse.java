package pl.scoottrack.repair.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Reprezentuje odpowiedź zawierającą listę napraw hulajnogi")
public record RepairListResponse(
        @Schema(description = "Tytuł naprawy", example = "Wymiana kół")
        String title,

        @Schema(description = "Cena naprawy", example = "250.00")
        BigDecimal price,

        @Schema(description = "UUID naprawy", example = "987e4567-e89b-12d3-a456-426614174000")
        UUID uuid,

        @Schema(description = "UUID hulajnogi", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID scooterUuid
) {}
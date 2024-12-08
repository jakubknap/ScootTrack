package pl.scoottrack.admin.repair.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Reprezentuje odpowiedź z listą napraw")
public record AdminRepairListResponse(
        @Schema(description = "Tytuł naprawy", example = "Wymiana kół")
        String title,

        @Schema(description = "Opis naprawy", example = "Koła zostały wymienione na nowe")
        String description,

        @Schema(description = "Koszt naprawy", example = "250.00")
        BigDecimal price,

        @Schema(description = "Data utworzenia naprawy", example = "2024-12-01T12:34:56")
        LocalDateTime createdDate,

        @Schema(description = "UUID hulajnogi związanej z naprawą", example = "123e4567-e89b-12d3-a456-426614174001")
        UUID scooterUuid,

        @Schema(description = "UUID naprawy", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID repairUuid
) {}
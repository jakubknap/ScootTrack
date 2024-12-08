package pl.scoottrack.repair.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Reprezentuje odpowiedź zawierającą statystyki napraw użytkownika")
public record StatsResponse(
        @Schema(description = "Łączna liczba napraw", example = "10")
        int totalRepairs,

        @Schema(description = "Łączny koszt napraw", example = "1500.00")
        BigDecimal totalCostsOfRepairs,

        @Schema(description = "Średni koszt naprawy", example = "150.00")
        BigDecimal averageCostsOfRepair
) {}
package pl.scoottrack.scooter.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditScooterRequest(

        @NotNull(message = "Uuid hulajnogi jest wymagany")
        @Schema(description = "Uuid hulajnogi do edytowania", required = true)
        UUID scooterUuid,

        @NotNull(message = "Nazwa jest wymagana")
        @NotEmpty(message = "Nazwa nie może być pusta")
        @Schema(description = "Nazwa hulajnogi", example = "Hulajnoga Elektryczna", required = true)
        String name,

        @NotNull(message = "Marka jest wymagana")
        @NotEmpty(message = "Marka nie może być pusta")
        @Schema(description = "Marka hulajnogi", example = "Xiaomi", required = true)
        String brand,

        @Schema(description = "Model hulajnogi", example = "M365")
        String model,

        @NotNull(message = "Typ hulajnogi jest wymagany")
        @NotEmpty(message = "Typ hulajnogi nie może być pusty")
        @Schema(description = "Typ hulajnogi", example = "elektryczna", required = true)
        String type
) {}
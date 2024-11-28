package pl.scoottrack.scooter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AddScooterRequest(

        @NotNull(message = "Nazwa jest wymagana")
        @NotEmpty(message = "Nazwa nie może być pusta")
        String name,

        @NotNull(message = "Marka jest wymagana")
        @NotEmpty(message = "Marka nie może być pusta")
        String brand,

        String model,

        @NotNull(message = "Typ hulajnogi jest wymagany")
        @NotEmpty(message = "Typ hulajnogi nie może być pusty")
        String type
) {}
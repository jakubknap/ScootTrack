package pl.scoottrack.admin.scooter.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Reprezentuje odpowiedź z listą hulajnóg")
public record AdminScooterListResponse(
        @Schema(description = "Nazwa hulajnogi", example = "Hulajnoga Pro 500")
        String name,

        @Schema(description = "Marka hulajnogi", example = "Xiaomi")
        String brand,

        @Schema(description = "Model hulajnogi", example = "Mi Pro 2")
        String model,

        @Schema(description = "Typ hulajnogi", example = "Elektryczna")
        String type,

        @Schema(description = "UUID użytkownika, który jest właścicielem hulajnogi", example = "123e4567-e89b-12d3-a456-426614174001")
        UUID userUuid,

        @Schema(description = "Imię właściciela hulajnogi", example = "Jan")
        String userFirstname,

        @Schema(description = "Liczba napraw hulajnogi", example = "3")
        Integer countOfRepairs
) {}
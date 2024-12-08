package pl.scoottrack.scooter.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScooterListResponse(

        @Schema(description = "Nazwa hulajnogi", example = "Hulajnoga Elektryczna")
        String name,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        @Schema(description = "Data utworzenia hulajnogi", example = "01-12-2023 10:30:00")
        LocalDateTime createdDate,

        @Schema(description = "Uuid hulajnogi", required = true)
        UUID scooterUuid
) {}
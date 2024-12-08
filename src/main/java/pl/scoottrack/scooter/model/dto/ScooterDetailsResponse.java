package pl.scoottrack.scooter.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Szczegóły hulajnogi")
public class ScooterDetailsResponse {

    @Schema(description = "Nazwa hulajnogi", example = "Hulajnoga Elektryczna")
    private String name;

    @Schema(description = "Marka hulajnogi", example = "Xiaomi")
    private String brand;

    @Schema(description = "Model hulajnogi", example = "M365")
    private String model;

    @Schema(description = "Typ hulajnogi", example = "elektryczna")
    private String type;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(description = "Data utworzenia hulajnogi", example = "01-12-2023 10:30:00")
    private LocalDateTime createdDate;

    @Schema(description = "Imię użytkownika przypisanego do hulajnogi", example = "Jan")
    private String userFirstname;

    @Schema(description = "Uuid hulajnogi", required = true)
    private UUID scooterUuid;

    @Schema(description = "Uuid użytkownika przypisanego do hulajnogi", required = true)
    private UUID userUuid;
}
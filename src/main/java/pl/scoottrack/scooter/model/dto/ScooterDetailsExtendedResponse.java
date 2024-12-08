package pl.scoottrack.scooter.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import pl.scoottrack.repair.model.dto.RepairDetailsResponse;

import java.util.List;

@Getter
@Setter
@Schema(description = "Rozszerzone szczegóły hulajnogi z naprawami")
public class ScooterDetailsExtendedResponse extends ScooterDetailsResponse {

    @Schema(description = "Lista napraw przypisanych do hulajnogi")
    private List<RepairDetailsResponse> repairs;

    public ScooterDetailsExtendedResponse(ScooterDetailsResponse scooterDetailsResponse, List<RepairDetailsResponse> repairs) {
        super(scooterDetailsResponse.getName(),
              scooterDetailsResponse.getBrand(),
              scooterDetailsResponse.getModel(),
              scooterDetailsResponse.getType(),
              scooterDetailsResponse.getCreatedDate(),
              scooterDetailsResponse.getUserFirstname(),
              scooterDetailsResponse.getScooterUuid(),
              scooterDetailsResponse.getUserUuid());
        this.repairs = repairs;
    }
}
package pl.scoottrack.scooter;

import lombok.Getter;
import lombok.Setter;
import pl.scoottrack.repair.RepairDetailsResponse;

import java.util.List;

@Getter
@Setter
public class ScooterDetailsExtendedResponse extends ScooterDetailsResponse {

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
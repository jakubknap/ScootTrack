package pl.scoottrack.scooter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.scoottrack.repair.RepairDetailsResponse;

import java.util.List;
import java.util.UUID;

public interface ScooterService {

    void addScooter(AddScooterRequest request);

    void editScooter(EditScooterRequest request);

    void deleteScooter(UUID scooterUuid);

    ScooterDetailsResponse getScooterDetails(UUID scooterUuid);

    ScooterDetailsExtendedResponse getScooterDetailsExtended(UUID scooterUuid);

    Page<ScooterListResponse> getAllScooters(Pageable pageable);

    List<RepairDetailsResponse> getScooterRepairs(UUID scooterUuid);
}
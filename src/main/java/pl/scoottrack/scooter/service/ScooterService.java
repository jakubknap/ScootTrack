package pl.scoottrack.scooter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.scoottrack.repair.model.dto.RepairDetailsResponse;
import pl.scoottrack.scooter.model.dto.AddScooterRequest;
import pl.scoottrack.scooter.model.dto.EditScooterRequest;
import pl.scoottrack.scooter.model.dto.ScooterDetailsExtendedResponse;
import pl.scoottrack.scooter.model.dto.ScooterDetailsResponse;
import pl.scoottrack.scooter.model.dto.ScooterListResponse;

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
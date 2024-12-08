package pl.scoottrack.admin.scooter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.scoottrack.admin.scooter.dto.AdminScooterListResponse;
import pl.scoottrack.scooter.model.dto.EditScooterRequest;

import java.util.UUID;

public interface AdminScooterService {

    Page<AdminScooterListResponse> getAllScooters(Pageable pageable);

    void editScooter(EditScooterRequest request);

    void deleteScooter(UUID uuid);
}
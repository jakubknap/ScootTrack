package pl.scoottrack.admin.scooter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.scoottrack.scooter.EditScooterRequest;

import java.util.UUID;

public interface AdminScooterService {

    Page<AdminScooterListResponse> getAllScooters(Pageable pageable);

    void editScooter(EditScooterRequest request);

    void deleteScooter(UUID uuid);
}

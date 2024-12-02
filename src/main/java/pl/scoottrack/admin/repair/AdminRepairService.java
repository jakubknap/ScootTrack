package pl.scoottrack.admin.repair;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.scoottrack.repair.EditRepairRequest;

import java.util.UUID;

public interface AdminRepairService {
    Page<AdminRepairListResponse> getAllRepairs(Pageable pageable);

    void editRepair(EditRepairRequest request);

    void deleteRepair(UUID uuid);
}
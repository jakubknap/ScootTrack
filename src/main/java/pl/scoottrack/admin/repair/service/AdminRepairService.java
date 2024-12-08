package pl.scoottrack.admin.repair.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.scoottrack.admin.repair.dto.AdminRepairListResponse;
import pl.scoottrack.repair.model.dto.EditRepairRequest;

import java.util.UUID;

public interface AdminRepairService {
    Page<AdminRepairListResponse> getAllRepairs(Pageable pageable);

    void editRepair(EditRepairRequest request);

    void deleteRepair(UUID uuid);
}
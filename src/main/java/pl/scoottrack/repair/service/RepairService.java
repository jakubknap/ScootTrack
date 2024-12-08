package pl.scoottrack.repair.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.scoottrack.repair.model.dto.AddRepairRequest;
import pl.scoottrack.repair.model.dto.EditRepairRequest;
import pl.scoottrack.repair.model.dto.RepairDetailsResponse;
import pl.scoottrack.repair.model.dto.RepairListResponse;
import pl.scoottrack.repair.model.dto.StatsResponse;

import java.util.UUID;

public interface RepairService {

    void addRepair(AddRepairRequest request);

    void editRepair(EditRepairRequest request);

    void deleteRepair(UUID repairUuid);

    RepairDetailsResponse getRepairDetails(UUID repairUuid);

    Page<RepairListResponse> getAllRepairs(Pageable pageable);

    StatsResponse getStats();
}
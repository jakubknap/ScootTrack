package pl.scoottrack.admin.repair;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.scoottrack.repair.EditRepairRequest;
import pl.scoottrack.repair.Repair;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminRepairServiceImpl implements AdminRepairService {

    private final AdminRepairRepository repairRepository;

    @Override
    public Page<AdminRepairListResponse> getAllRepairs(Pageable pageable) {
        return repairRepository.findAllRepairs(pageable);
    }

    @Override
    public void editRepair(EditRepairRequest request) {
        Repair repair = findRepair(request.repairUuid());

        repair.setTitle(request.title());
        repair.setDescription(request.description());
        repair.setPrice(request.price());

        repairRepository.save(repair);
        log.info("Repair successfully edited");
    }

    @Override
    public void deleteRepair(UUID uuid) {
        repairRepository.deleteByUuid(uuid);
        log.info("Repair deleted successfully");
    }

    private Repair findRepair(UUID repairUuid) {
        return repairRepository.findByUuid(repairUuid)
                               .orElseThrow(() -> {
                                   log.error("Repair with uuid: {} not found", repairUuid);
                                   return new EntityNotFoundException("Nie znaleziono naprawy");
                               });
    }
}
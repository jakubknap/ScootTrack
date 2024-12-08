package pl.scoottrack.admin.repair.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.scoottrack.admin.repair.dto.AdminRepairListResponse;
import pl.scoottrack.admin.repair.repository.AdminRepairRepository;
import pl.scoottrack.admin.repair.service.AdminRepairService;
import pl.scoottrack.admin.user.repository.AdminUserRepository;
import pl.scoottrack.notification.service.NotificationService;
import pl.scoottrack.repair.model.Repair;
import pl.scoottrack.repair.model.dto.EditRepairRequest;
import pl.scoottrack.user.model.User;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminRepairServiceImpl implements AdminRepairService {

    private final AdminRepairRepository repairRepository;
    private final NotificationService notificationService;
    private final AdminUserRepository userRepository;

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
        User user = getUser(repair.getUserUuid());
        notificationService.sendNotification(user.getEmail(), "Twoja naprawa hulajnogi została edytowana przez administratora");
        log.info("Repair successfully edited");
    }

    @Override
    public void deleteRepair(UUID uuid) {
        Repair repair = findRepair(uuid);
        repairRepository.deleteByUuid(uuid);
        User user = getUser(repair.getUserUuid());
        notificationService.sendNotification(user.getEmail(), "Twoja naprawa hulajnogi została usunięta przez administratora");
        log.info("Repair deleted successfully");
    }

    private Repair findRepair(UUID repairUuid) {
        return repairRepository.findByUuid(repairUuid)
                               .orElseThrow(() -> {
                                   log.error("Repair with uuid: {} not found", repairUuid);
                                   return new EntityNotFoundException("Nie znaleziono naprawy");
                               });
    }

    private User getUser(UUID userUuid) {
        return userRepository.findByUuid(userUuid)
                             .orElseThrow(() -> {
                                 log.error("User with uuid: {} not found", userUuid);
                                 return new EntityNotFoundException("Nie znaleziono użytkownika o podanym uuidzie");
                             });
    }
}
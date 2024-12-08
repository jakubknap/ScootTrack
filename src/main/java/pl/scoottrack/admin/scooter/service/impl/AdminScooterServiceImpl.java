package pl.scoottrack.admin.scooter.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.scoottrack.admin.scooter.dto.AdminScooterListProjection;
import pl.scoottrack.admin.scooter.dto.AdminScooterListResponse;
import pl.scoottrack.admin.scooter.repository.AdminScooterRepository;
import pl.scoottrack.admin.scooter.service.AdminScooterService;
import pl.scoottrack.notification.service.NotificationService;
import pl.scoottrack.scooter.model.Scooter;
import pl.scoottrack.scooter.model.dto.EditScooterRequest;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminScooterServiceImpl implements AdminScooterService {

    private final AdminScooterRepository scooterRepository;
    private final NotificationService notificationService;

    @Override
    public Page<AdminScooterListResponse> getAllScooters(Pageable pageable) {
        Page<AdminScooterListProjection> allScooters = scooterRepository.getAllScooters(pageable);

        List<AdminScooterListResponse> content = allScooters.getContent()
                                                            .stream()
                                                            .map(this::mapToResponse)
                                                            .toList();

        return new PageImpl<>(content, pageable, allScooters.getTotalElements());
    }

    @Override
    public void editScooter(EditScooterRequest request) {
        Scooter scooter = getScooter(request.scooterUuid());

        scooter.setName(request.name());
        scooter.setBrand(request.brand());
        scooter.setModel(request.model());
        scooter.setType(request.type());

        scooterRepository.save(scooter);

        notificationService.sendNotification(scooter.getUser()
                                                    .getEmail(), "Twoja hulajnoga została edytowana przez administratora");
        log.info("Scooter edited successfully");
    }

    @Override
    public void deleteScooter(UUID uuid) {
        Scooter scooter = getScooter(uuid);
        scooterRepository.deleteByUuid(uuid);
        notificationService.sendNotification(scooter.getUser()
                                                    .getEmail(), "Twoja hulajnoga została usunięta przez administratora");
        log.info("Scooter deleted successfully");
    }

    private AdminScooterListResponse mapToResponse(AdminScooterListProjection projection) {
        return new AdminScooterListResponse(projection.getName(),
                                            projection.getBrand(),
                                            projection.getModel(),
                                            projection.getType(),
                                            projection.getUserUuid(),
                                            projection.getUserFirstname(),
                                            projection.getCountOfRepairs());
    }

    private Scooter getScooter(UUID scooterUuid) {
        return scooterRepository.findByUuid(scooterUuid)
                                .orElseThrow(() -> {
                                    log.error("Scooter with uuid: {} not found", scooterUuid);
                                    return new EntityNotFoundException("Nie znaleziono hulajnogi o podanym uuidzie");
                                });
    }
}
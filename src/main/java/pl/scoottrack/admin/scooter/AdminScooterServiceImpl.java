package pl.scoottrack.admin.scooter;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.scoottrack.scooter.EditScooterRequest;
import pl.scoottrack.scooter.Scooter;
import pl.scoottrack.scooter.ScooterServiceImpl;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminScooterServiceImpl implements AdminScooterService {

    private final AdminScooterRepository scooterRepository;
    private final ScooterServiceImpl scooterServiceImpl;

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
        Scooter scooter = getScooter(request);

        scooter.setName(request.name());
        scooter.setBrand(request.brand());
        scooter.setModel(request.model());
        scooter.setType(request.type());

        scooterRepository.save(scooter);

        log.info("Scooter edited successfully");
    }

    @Override
    public void deleteScooter(UUID uuid) {
        scooterRepository.deleteByUuid(uuid);
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

    private Scooter getScooter(EditScooterRequest request) {
        return scooterRepository.findByUuid(request.scooterUuid())
                                .orElseThrow(() -> {
                                    log.error("Scooter with uuid: {} not found", request.scooterUuid());
                                    return new EntityNotFoundException("Nie znaleziono hulajnogi o podanym uuidzie");
                                });
    }
}
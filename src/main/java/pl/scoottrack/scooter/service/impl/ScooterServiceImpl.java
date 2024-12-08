package pl.scoottrack.scooter.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.scoottrack.repair.model.dto.RepairDetailsResponse;
import pl.scoottrack.repair.repository.RepairRepository;
import pl.scoottrack.scooter.model.Scooter;
import pl.scoottrack.scooter.model.dto.AddScooterRequest;
import pl.scoottrack.scooter.model.dto.EditScooterRequest;
import pl.scoottrack.scooter.model.dto.ScooterDetailsExtendedResponse;
import pl.scoottrack.scooter.model.dto.ScooterDetailsResponse;
import pl.scoottrack.scooter.model.dto.ScooterListResponse;
import pl.scoottrack.scooter.repository.ScooterRepository;
import pl.scoottrack.scooter.service.ScooterService;
import pl.scoottrack.user.model.User;

import java.util.List;
import java.util.UUID;

import static pl.scoottrack.security.SecurityUtils.getLoggedUser;
import static pl.scoottrack.security.SecurityUtils.getLoggedUserUUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class ScooterServiceImpl implements ScooterService {

    private static final int MAX_PAGE_SIZE = 5;

    private final ScooterRepository scooterRepository;
    private final RepairRepository repairRepository;

    @Override
    public void addScooter(AddScooterRequest request) {
        User user = getLoggedUser();
        scooterRepository.save(buildScooter(request, user));
        log.info("Scooter added");
    }

    @Override
    public void editScooter(EditScooterRequest request) {
        Scooter scooter = findScooter(request.scooterUuid());
        User user = getLoggedUser();

        if (!scooter.getUser()
                    .getUuid()
                    .equals(user.getUuid())) {
            log.error("User is not authorized to modify this scooter. User: {}, Scooter Owner: {}",
                      user.getUuid(),
                      scooter.getUser()
                             .getUuid());
            throw new RuntimeException("Nie jesteś upoważniony do edycji tej hulajnogi");
        }

        scooter.setName(request.name());
        scooter.setBrand(request.brand());
        scooter.setModel(request.model());
        scooter.setType(request.type());

        scooterRepository.save(scooter);
        log.info("Scooter edited");
    }

    @Override
    public void deleteScooter(UUID scooterUuid) {
        Scooter scooter = findScooter(scooterUuid);
        UUID loggedUserUUID = getLoggedUserUUID();

        if (!scooter.getUser()
                    .getUuid()
                    .equals(loggedUserUUID)) {
            log.error("User is not authorized to delete this scooter. User: {}, Scooter Owner: {}",
                      loggedUserUUID,
                      scooter.getUser()
                             .getUuid());
            throw new RuntimeException("Nie jesteś upoważniony do usunięcia tej hulajnogi");
        }

        scooterRepository.delete(scooter);
        log.info("Scooter deleted");
    }

    @Override
    public ScooterDetailsResponse getScooterDetails(UUID scooterUuid) {
        Scooter scooter = findScooter(scooterUuid);
        UUID loggedUserUUID = getLoggedUserUUID();

        if (!scooter.getUser()
                    .getUuid()
                    .equals(loggedUserUUID)) {
            log.error("User is not authorized to view details of this scooter. User: {}, Scooter Owner: {}",
                      loggedUserUUID,
                      scooter.getUser()
                             .getUuid());
            throw new RuntimeException("Nie jesteś upoważniony do podglądu tej hulajnogi");
        }

        return new ScooterDetailsResponse(scooter.getName(),
                                          scooter.getBrand(),
                                          scooter.getModel(),
                                          scooter.getType(),
                                          scooter.getCreatedDate(),
                                          scooter.getUser()
                                                 .getFirstname(),
                                          scooter.getUuid(),
                                          scooter.getUser()
                                                 .getUuid());
    }

    @Override
    public ScooterDetailsExtendedResponse getScooterDetailsExtended(UUID scooterUuid) {
        ScooterDetailsResponse scooterDetails = getScooterDetails(scooterUuid);
        List<RepairDetailsResponse> allRepairsByScooterUuid = repairRepository.findAllRepairsByScooterUuid(scooterUuid);
        return new ScooterDetailsExtendedResponse(scooterDetails, allRepairsByScooterUuid);
    }

    @Override
    public Page<ScooterListResponse> getAllScooters(Pageable pageable) {
        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            pageable = PageRequest.of(pageable.getPageNumber(), MAX_PAGE_SIZE, pageable.getSort());
        }

        return scooterRepository.findAllByUserUuid(getLoggedUserUUID(), pageable);
    }

    @Override
    public List<RepairDetailsResponse> getScooterRepairs(UUID scooterUuid) {
        Scooter scooter = findScooter(scooterUuid);
        UUID loggedUserUUID = getLoggedUserUUID();

        if (!scooter.getUser()
                    .getUuid()
                    .equals(loggedUserUUID)) {
            log.error("User is not authorized to view repairs of this scooter. User: {}, Scooter Owner: {}",
                      loggedUserUUID,
                      scooter.getUser()
                             .getUuid());
            throw new RuntimeException("Nie jesteś upoważniony do podglądu napraw tej hulajnogi");
        }

        return repairRepository.findAllRepairsByScooterUuid(scooterUuid);
    }

    private Scooter buildScooter(AddScooterRequest request, User user) {
        return Scooter.builder()
                      .name(request.name())
                      .brand(request.brand())
                      .model(request.model())
                      .type(request.type())
                      .user(user)
                      .build();
    }

    private Scooter findScooter(UUID scooterUuid) {
        return scooterRepository.findByUuid(scooterUuid)
                                .orElseThrow(() -> {
                                    log.error("Scooter with uuid: {} not found", scooterUuid);
                                    return new EntityNotFoundException("Nie znaleziono hulajnogi");
                                });
    }
}
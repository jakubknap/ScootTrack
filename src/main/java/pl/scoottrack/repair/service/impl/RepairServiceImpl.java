package pl.scoottrack.repair.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.scoottrack.repair.model.Repair;
import pl.scoottrack.repair.model.dto.AddRepairRequest;
import pl.scoottrack.repair.model.dto.EditRepairRequest;
import pl.scoottrack.repair.model.dto.RepairDetailsResponse;
import pl.scoottrack.repair.model.dto.RepairListResponse;
import pl.scoottrack.repair.model.dto.StatsResponse;
import pl.scoottrack.repair.repository.RepairRepository;
import pl.scoottrack.repair.service.RepairService;
import pl.scoottrack.scooter.model.Scooter;
import pl.scoottrack.scooter.repository.ScooterRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static pl.scoottrack.security.SecurityUtils.getLoggedUser;
import static pl.scoottrack.security.SecurityUtils.getLoggedUserUUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class RepairServiceImpl implements RepairService {

    private static final int MAX_PAGE_SIZE = 5;

    private final RepairRepository repairRepository;
    private final ScooterRepository scooterRepository;

    @Override
    public void addRepair(AddRepairRequest request) {
        Scooter scooter = findScooter(request.scooterUuid());
        UUID loggedUserUUID = getLoggedUserUUID();

        if (!scooter.getUser()
                    .getUuid()
                    .equals(loggedUserUUID)) {
            log.error("User is not authorized to add repair to this scooter. User: {}, Scooter Owner: {}",
                      loggedUserUUID,
                      scooter.getUser()
                             .getUuid());
            throw new RuntimeException("Nie jesteś upoważniony do dodania naprawy do tej hulajnogi");
        }

        Repair repair = buildRepair(request, scooter, loggedUserUUID);

        repairRepository.save(repair);
        log.info("Repair successfully added to the scooter");
    }

    @Override
    public void editRepair(EditRepairRequest request) {
        Repair repair = findRepair(request.repairUuid());

        if (!repair.getCreatedBy()
                   .equals(getLoggedUser().getId())) {
            log.error("User is not authorized to edit this repair. User: {}, Repair Owner: {}", getLoggedUser().getId(), repair.getCreatedBy());
            throw new RuntimeException("Nie jesteś upoważniony do edycji tej naprawy");
        }

        repair.setTitle(request.title());
        repair.setDescription(request.description());
        repair.setPrice(request.price());

        repairRepository.save(repair);
        log.info("Repair successfully edited");
    }

    @Override
    public void deleteRepair(UUID repairUuid) {
        Repair repair = findRepair(repairUuid);

        if (!repair.getCreatedBy()
                   .equals(getLoggedUser().getId())) {
            log.error("User is not authorized to delete this repair. User: {}, Repair Owner: {}", getLoggedUser().getId(), repair.getCreatedBy());
            throw new RuntimeException("Nie jesteś upoważniony do usunięcia tej naprawy");
        }

        repairRepository.delete(repair);
        log.info("Repair successfully deleted");
    }

    @Override
    public RepairDetailsResponse getRepairDetails(UUID repairUuid) {
        Repair repair = findRepair(repairUuid);

        if (!repair.getCreatedBy()
                   .equals(getLoggedUser().getId())) {
            log.error("User is not authorized to view details of this repair. User: {}, Repair Owner: {}", getLoggedUser().getId(), repair.getCreatedBy());
            throw new RuntimeException("Nie jesteś upoważniony do podglądu tej naprawy");
        }
        return new RepairDetailsResponse(repair.getTitle(),
                                         repair.getDescription(),
                                         repair.getPrice(),
                                         repair.getCreatedDate(),
                                         repair.getScooter()
                                               .getUuid(),
                                         repair.getUuid());
    }

    @Override
    public Page<RepairListResponse> getAllRepairs(Pageable pageable) {
        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            pageable = PageRequest.of(pageable.getPageNumber(), MAX_PAGE_SIZE, pageable.getSort());
        }

        return repairRepository.findAllByUserUuid(getLoggedUserUUID(), pageable);
    }

    @Override
    public StatsResponse getStats() {
        List<BigDecimal> allRepairCostsByUserUuid = repairRepository.findAllRepairCostsByUserUuid(getLoggedUserUUID());

        int totalRepairs = allRepairCostsByUserUuid.size();
        BigDecimal totalCostsOfRepairs = allRepairCostsByUserUuid.stream()
                                                                 .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal averageCostsOfRepair = totalCostsOfRepairs.divide(BigDecimal.valueOf(totalRepairs), RoundingMode.HALF_UP);

        return new StatsResponse(totalRepairs, totalCostsOfRepairs, averageCostsOfRepair);
    }

    private Scooter findScooter(UUID scooterUuid) {
        return scooterRepository.findByUuid(scooterUuid)
                                .orElseThrow(() -> {
                                    log.error("Scooter with uuid: {} not found", scooterUuid);
                                    return new EntityNotFoundException("Nie znaleziono hulajnogi");
                                });
    }

    private Repair buildRepair(AddRepairRequest request, Scooter scooter, UUID loggedUserUUID) {
        return Repair.builder()
                     .title(request.title())
                     .description(request.description())
                     .price(request.price())
                     .scooter(scooter)
                     .userUuid(loggedUserUUID)
                     .build();
    }

    private Repair findRepair(UUID repairUuid) {
        return repairRepository.findByUuid(repairUuid)
                               .orElseThrow(() -> {
                                   log.error("Repair with uuid: {} not found", repairUuid);
                                   return new EntityNotFoundException("Nie znaleziono naprawy");
                               });
    }
}
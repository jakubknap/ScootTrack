package pl.scoottrack.repair.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.scoottrack.repair.model.dto.AddRepairRequest;
import pl.scoottrack.repair.model.dto.EditRepairRequest;
import pl.scoottrack.repair.model.dto.RepairDetailsResponse;
import pl.scoottrack.repair.model.dto.RepairListResponse;
import pl.scoottrack.repair.model.dto.StatsResponse;
import pl.scoottrack.repair.service.RepairService;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;

@Log4j2
@RestController
@RequestMapping("/repair")
@RequiredArgsConstructor
@Tag(name = "Repair")
public class RepairController {

    private final RepairService repairService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void addRepair(@RequestBody @Valid AddRepairRequest request) {
        log.info("Add repair request: {}", request);
        repairService.addRepair(request);
    }

    @PutMapping
    public void editRepair(@RequestBody @Valid EditRepairRequest request) {
        log.info("Edit repair request: {}", request);
        repairService.editRepair(request);
    }

    @DeleteMapping("/{uuid}")
    public void deleteRepair(@PathVariable UUID uuid) {
        log.info("Delete repair with uuid: {}", uuid);
        repairService.deleteRepair(uuid);
    }

    @GetMapping("/{uuid}")
    public RepairDetailsResponse getRepairDetails(@PathVariable UUID uuid) {
        log.info("Get repair details with uuid: {}", uuid);
        return repairService.getRepairDetails(uuid);
    }

    @GetMapping("/search")
    public Page<RepairListResponse> getAllRepairs(@PageableDefault(size = 2, sort = "createdDate", direction = DESC) Pageable pageable) {
        log.info("Get all user repairs");
        return repairService.getAllRepairs(pageable);
    }

    @GetMapping("/stats")
    public StatsResponse getStats() {
        log.info("Get user repair stats");
        return repairService.getStats();
    }
}
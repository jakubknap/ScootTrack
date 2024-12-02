package pl.scoottrack.admin.repair;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.scoottrack.repair.EditRepairRequest;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/admin/repair")
@RequiredArgsConstructor
public class AdminRepairController {

    private final AdminRepairService repairService;

    @GetMapping
    public Page<AdminRepairListResponse> getAllRepairs(Pageable pageable) {
        log.info("Get all repairs");
        return repairService.getAllRepairs(pageable);
    }

    @PutMapping
    public void editRepair(@RequestBody @Valid EditRepairRequest request) {
        log.info("Edit repair with uuid: {}", request.repairUuid());
        repairService.editRepair(request);
    }

    @DeleteMapping("/{uuid}")
    public void deleteRepair(@PathVariable String uuid) {
        log.info("Delete repair with uuid: {}", uuid);
        repairService.deleteRepair(UUID.fromString(uuid));
    }
}
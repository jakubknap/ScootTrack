package pl.scoottrack.scooter;

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
import pl.scoottrack.repair.RepairDetailsResponse;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;

@Log4j2
@RestController
@RequestMapping("/scooter")
@RequiredArgsConstructor
@Tag(name = "Scooter")
public class ScooterController {

    private final ScooterService scooterService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void addScooter(@RequestBody @Valid AddScooterRequest request) {
        log.info("Add scooter request: {}", request);
        scooterService.addScooter(request);
    }

    @PutMapping
    public void editScooter(@RequestBody @Valid EditScooterRequest request) {
        log.info("Edit scooter request: {}", request);
        scooterService.editScooter(request);
    }

    @DeleteMapping("/{uuid}")
    public void deleteScooter(@PathVariable UUID uuid) {
        log.info("Delete scooter with uuid: {}", uuid);
        scooterService.deleteScooter(uuid);
    }

    @GetMapping("/{uuid}")
    public ScooterDetailsResponse getScooterDetails(@PathVariable UUID uuid) {
        log.info("Get scooter details with uuid: {}", uuid);
        return scooterService.getScooterDetails(uuid);
    }

    @GetMapping("/{uuid}/extended")
    public ScooterDetailsExtendedResponse getScooterDetailsExtended(@PathVariable UUID uuid) {
        log.info("Get scooter details with repairs for scooter with uuid: {}", uuid);
        return scooterService.getScooterDetailsExtended(uuid);
    }

    @GetMapping("/search")
    public Page<ScooterListResponse> getAllScooters(@PageableDefault(size = 2, sort = "createdDate", direction = DESC) Pageable pageable) {
        log.info("Get all user scooters");
        return scooterService.getAllScooters(pageable);
    }

    @GetMapping("/{uuid}/repairs")
    public List<RepairDetailsResponse> getScooterRepairs(@PathVariable UUID uuid) {
        log.info("Get scooter repairs for scooter with uuid: {}", uuid);
        return scooterService.getScooterRepairs(uuid);
    }
}
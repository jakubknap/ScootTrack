package pl.scoottrack.admin.scooter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.scoottrack.scooter.EditScooterRequest;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/admin/scooter")
@RequiredArgsConstructor
public class AdminScooterController {

    private final AdminScooterService scooterService;

    @GetMapping
    public Page<AdminScooterListResponse> getAllScooters(@PageableDefault(sort = "name") Pageable pageable) {
        log.info("Get all scooters");
        return scooterService.getAllScooters(pageable);
    }

    @PutMapping
    public void editScooter(@RequestBody @Valid EditScooterRequest request) {
        log.info("Edit scooter with uuid: {}", request.scooterUuid());
        scooterService.editScooter(request);
    }

    @DeleteMapping("/{uuid}")
    public void deleteScooter(@PathVariable String uuid) {
        log.info("Delete scooter with uuid: {}", uuid);
        scooterService.deleteScooter(UUID.fromString(uuid));
    }
}
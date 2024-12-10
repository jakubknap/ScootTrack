package pl.scoottrack.admin.repair.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import pl.scoottrack.admin.repair.dto.AdminRepairListResponse;
import pl.scoottrack.admin.repair.service.AdminRepairService;
import pl.scoottrack.repair.model.dto.EditRepairRequest;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/admin/repair")
@RequiredArgsConstructor
@Tag(name = "Admin Repair Management", description = "Endpointy do zarządzania naprawami hulajnóg przez administratora")
public class AdminRepairController {

    private final AdminRepairService repairService;

    @Operation(
            summary = "Pobierz listę napraw",
            description = "Zwraca paginowaną listę napraw zarejestrowanych w systemie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista napraw została pomyślnie pobrana"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera", content = @Content)
            }
    )
    @GetMapping
    public Page<AdminRepairListResponse> getAllRepairs(Pageable pageable) {
        log.info("Get all repairs");
        return repairService.getAllRepairs(pageable);
    }

    @Operation(
            summary = "Edytuj istniejącą naprawę",
            description = "Aktualizuje dane naprawy w oparciu o przesłane żądanie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Naprawa została pomyślnie zaktualizowana"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono naprawy o podanym UUID"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @PutMapping
    public void editRepair(@RequestBody @Valid EditRepairRequest request) {
        log.info("Edit repair with uuid: {}", request.repairUuid());
        repairService.editRepair(request);
    }

    @Operation(
            summary = "Usuń naprawę",
            description = "Usuwa naprawę o podanym UUID z systemu",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Naprawa została pomyślnie usunięta"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono naprawy o podanym UUID"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @DeleteMapping("/{uuid}")
    public void deleteRepair(@PathVariable String uuid) {
        log.info("Delete repair with uuid: {}", uuid);
        repairService.deleteRepair(UUID.fromString(uuid));
    }
}
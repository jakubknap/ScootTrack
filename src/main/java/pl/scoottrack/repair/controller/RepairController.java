package pl.scoottrack.repair.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Repair", description = "Endpointy do zarządzania naprawami hulajnóg")
public class RepairController {

    private final RepairService repairService;

    @Operation(
            summary = "Dodaj naprawę do hulajnogi",
            description = "Dodaje nową naprawę do wybranej hulajnogi przypisanej do użytkownika",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Naprawa została pomyślnie dodana"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe"),
                    @ApiResponse(responseCode = "403", description = "Brak uprawnień do dodania naprawy"),
                    @ApiResponse(responseCode = "404", description = "Hulajnoga nie istnieje"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @PostMapping
    @ResponseStatus(CREATED)
    public void addRepair(@RequestBody @Valid AddRepairRequest request) {
        log.info("Add repair request: {}", request);
        repairService.addRepair(request);
    }

    @Operation(
            summary = "Edytuj istniejącą naprawę",
            description = "Pozwala edytować istniejącą naprawę przypisaną do użytkownika",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Naprawa została pomyślnie edytowana"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe"),
                    @ApiResponse(responseCode = "403", description = "Brak uprawnień do edycji naprawy"),
                    @ApiResponse(responseCode = "404", description = "Naprawa nie istnieje"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @PutMapping
    public void editRepair(@RequestBody @Valid EditRepairRequest request) {
        log.info("Edit repair request: {}", request);
        repairService.editRepair(request);
    }

    @Operation(
            summary = "Usuń naprawę",
            description = "Usuwa wybraną naprawę przypisaną do użytkownika",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Naprawa została pomyślnie usunięta"),
                    @ApiResponse(responseCode = "403", description = "Brak uprawnień do usunięcia naprawy"),
                    @ApiResponse(responseCode = "404", description = "Naprawa nie istnieje"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @DeleteMapping("/{uuid}")
    public void deleteRepair(@PathVariable UUID uuid) {
        log.info("Delete repair with uuid: {}", uuid);
        repairService.deleteRepair(uuid);
    }

    @Operation(
            summary = "Pobierz szczegóły naprawy",
            description = "Zwraca szczegółowe informacje o wybranej naprawie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Szczegóły naprawy zostały pobrane"),
                    @ApiResponse(responseCode = "403", description = "Brak uprawnień do podglądu naprawy"),
                    @ApiResponse(responseCode = "404", description = "Naprawa nie istnieje"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @GetMapping("/{uuid}")
    public RepairDetailsResponse getRepairDetails(@PathVariable UUID uuid) {
        log.info("Get repair details with uuid: {}", uuid);
        return repairService.getRepairDetails(uuid);
    }

    @Operation(
            summary = "Pobierz wszystkie naprawy użytkownika",
            description = "Zwraca listę wszystkich napraw przypisanych do aktualnie zalogowanego użytkownika",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista napraw została pobrana"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @GetMapping("/search")
    public Page<RepairListResponse> getAllRepairs(@PageableDefault(size = 2, sort = "createdDate", direction = DESC) Pageable pageable) {
        log.info("Get all user repairs");
        return repairService.getAllRepairs(pageable);
    }

    @Operation(
            summary = "Pobierz statystyki napraw użytkownika",
            description = "Zwraca statystyki dotyczące wszystkich napraw użytkownika, takie jak ich liczba, suma kosztów i średni koszt naprawy",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Statystyki napraw zostały pobrane"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @GetMapping("/stats")
    public StatsResponse getStats() {
        log.info("Get user repair stats");
        return repairService.getStats();
    }
}
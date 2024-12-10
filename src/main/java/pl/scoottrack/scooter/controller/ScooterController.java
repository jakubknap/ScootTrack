package pl.scoottrack.scooter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
import pl.scoottrack.repair.model.dto.RepairDetailsResponse;
import pl.scoottrack.scooter.model.dto.AddScooterRequest;
import pl.scoottrack.scooter.model.dto.EditScooterRequest;
import pl.scoottrack.scooter.model.dto.ScooterDetailsExtendedResponse;
import pl.scoottrack.scooter.model.dto.ScooterDetailsResponse;
import pl.scoottrack.scooter.model.dto.ScooterListResponse;
import pl.scoottrack.scooter.service.ScooterService;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;

@Log4j2
@RestController
@RequestMapping("/scooter")
@RequiredArgsConstructor
@Tag(name = "Scooter", description = "Endpointy do zarządzania hulajnogami")
public class ScooterController {

    private final ScooterService scooterService;

    @Operation(
            summary = "Dodaj hulajnogę",
            description = "Dodaje nową hulajnogę do systemu",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Hulajnoga została pomyślnie dodana"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @PostMapping
    @ResponseStatus(CREATED)
    public void addScooter(@RequestBody @Valid AddScooterRequest request) {
        log.info("Add scooter request: {}", request);
        scooterService.addScooter(request);
    }

    @Operation(
            summary = "Edytuj hulajnogę",
            description = "Pozwala edytować istniejącą hulajnogę w systemie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Hulajnoga została pomyślnie edytowana"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe lub brak uprawnień do hulajnogi"),
                    @ApiResponse(responseCode = "404", description = "Hulajnoga nie istnieje"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @PutMapping
    public void editScooter(@RequestBody @Valid EditScooterRequest request) {
        log.info("Edit scooter request: {}", request);
        scooterService.editScooter(request);
    }

    @Operation(
            summary = "Usuń hulajnogę",
            description = "Usuwa wybraną hulajnogę z systemu",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Hulajnoga została pomyślnie usunięta"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe lub brak uprawnień do naprawy"),
                    @ApiResponse(responseCode = "404", description = "Hulajnoga nie istnieje"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @DeleteMapping("/{uuid}")
    public void deleteScooter(@PathVariable UUID uuid) {
        log.info("Delete scooter with uuid: {}", uuid);
        scooterService.deleteScooter(uuid);
    }

    @Operation(
            summary = "Pobierz szczegóły hulajnogi",
            description = "Zwraca szczegółowe informacje o wybranej hulajnodze",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Szczegóły hulajnogi zostały pobrane"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe lub brak uprawnień do naprawy", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Hulajnoga nie istnieje", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera", content = @Content)
            }
    )
    @GetMapping("/{uuid}")
    public ScooterDetailsResponse getScooterDetails(@PathVariable UUID uuid) {
        log.info("Get scooter details with uuid: {}", uuid);
        return scooterService.getScooterDetails(uuid);
    }

    @Operation(
            summary = "Pobierz szczegóły hulajnogi z naprawami",
            description = "Zwraca szczegółowe informacje o hulajnodze oraz jej naprawach",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Szczegóły hulajnogi z naprawami zostały pobrane"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe lub brak uprawnień do naprawy", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Hulajnoga nie istnieje", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera", content = @Content)
            }
    )
    @GetMapping("/{uuid}/extended")
    public ScooterDetailsExtendedResponse getScooterDetailsExtended(@PathVariable UUID uuid) {
        log.info("Get scooter details with repairs for scooter with uuid: {}", uuid);
        return scooterService.getScooterDetailsExtended(uuid);
    }

    @Operation(
            summary = "Pobierz wszystkie hulajnogi użytkownika",
            description = "Zwraca listę wszystkich hulajnóg przypisanych do aktualnie zalogowanego użytkownika",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista hulajnóg została pobrana"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera", content = @Content)
            }
    )
    @GetMapping("/search")
    public Page<ScooterListResponse> getAllScooters(@PageableDefault(size = 2, sort = "createdDate", direction = DESC) Pageable pageable) {
        log.info("Get all user scooters");
        return scooterService.getAllScooters(pageable);
    }

    @Operation(
            summary = "Pobierz naprawy hulajnogi",
            description = "Zwraca listę napraw przypisanych do wybranej hulajnogi",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista napraw hulajnogi została pobrana"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe lub brak uprawnień do naprawy", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Hulajnoga nie istnieje", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera", content = @Content)
            }
    )
    @GetMapping("/{uuid}/repairs")
    public List<RepairDetailsResponse> getScooterRepairs(@PathVariable UUID uuid) {
        log.info("Get scooter repairs for scooter with uuid: {}", uuid);
        return scooterService.getScooterRepairs(uuid);
    }
}
package pl.scoottrack.admin.scooter.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.scoottrack.admin.scooter.dto.AdminScooterListResponse;
import pl.scoottrack.admin.scooter.service.AdminScooterService;
import pl.scoottrack.scooter.model.dto.EditScooterRequest;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/admin/scooter")
@RequiredArgsConstructor
@Tag(name = "Admin Scooter Management", description = "Endpointy do zarządzania hulajnogami przez administratora")
public class AdminScooterController {

    private final AdminScooterService scooterService;

    @Operation(
            summary = "Pobierz listę hulajnóg",
            description = "Zwraca paginowaną listę hulajnóg dostępnych w systemie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista hulajnóg została pomyślnie pobrana"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @GetMapping
    public Page<AdminScooterListResponse> getAllScooters(@PageableDefault(sort = "name") Pageable pageable) {
        log.info("Get all scooters");
        return scooterService.getAllScooters(pageable);
    }

    @Operation(
            summary = "Edytuj hulajnogę",
            description = "Aktualizuje dane hulajnogi w oparciu o przesłane żądanie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Hulajnoga została pomyślnie zaktualizowana"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono hulajnogi o podanym UUID"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @PutMapping
    public void editScooter(@RequestBody @Valid EditScooterRequest request) {
        log.info("Edit scooter with uuid: {}", request.scooterUuid());
        scooterService.editScooter(request);
    }

    @Operation(
            summary = "Usuń hulajnogę",
            description = "Usuwa hulajnogę o podanym UUID z systemu",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Hulajnoga została pomyślnie usunięta"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono hulajnogi o podanym UUID"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @DeleteMapping("/{uuid}")
    public void deleteScooter(@PathVariable String uuid) {
        log.info("Delete scooter with uuid: {}", uuid);
        scooterService.deleteScooter(UUID.fromString(uuid));
    }
}
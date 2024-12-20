package pl.scoottrack.admin.user.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.scoottrack.admin.user.dto.EditUserRequest;
import pl.scoottrack.admin.user.dto.UsersListResponse;
import pl.scoottrack.admin.user.service.AdminUserService;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Log4j2
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
@Tag(name = "Admin User Management", description = "Endpointy do zarządzania użytkownikami przez administratora")
public class AdminUserController {

    private final AdminUserService userService;

    @Operation(
            summary = "Pobierz listę użytkowników",
            description = "Zwraca paginowaną listę użytkowników systemu",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista użytkowników została pomyślnie pobrana"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera", content = @Content)
            }
    )
    @GetMapping
    public Page<UsersListResponse> getAllUsers(@PageableDefault(size = 20, sort = "firstname", direction = DESC) Pageable pageable) {
        log.info("Get all users");
        return userService.getAllUsers(pageable);
    }

    @Operation(
            summary = "Edytuj użytkownika",
            description = "Aktualizuje dane użytkownika w oparciu o przesłane żądanie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Użytkownik został pomyślnie zaktualizowany"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono użytkownika o podanym UUID"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @PutMapping
    public void editUser(@RequestBody @Valid EditUserRequest request) {
        log.info("Edit user with uuid: {}", request.uuid());
        userService.editUser(request);
    }

    @Operation(
            summary = "Usuń użytkownika",
            description = "Usuwa użytkownika o podanym UUID z systemu",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Użytkownik został pomyślnie usunięty"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono użytkownika o podanym UUID"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @DeleteMapping("/{uuid}")
    public void deleteUser(@PathVariable String uuid) {
        log.info("Delete user with uuid: {}", uuid);
        userService.deleteUser(UUID.fromString(uuid));
    }
}
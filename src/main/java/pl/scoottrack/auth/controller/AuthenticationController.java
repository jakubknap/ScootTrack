package pl.scoottrack.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.scoottrack.auth.dto.AuthenticationRequest;
import pl.scoottrack.auth.dto.AuthenticationResponse;
import pl.scoottrack.auth.dto.RegistrationRequest;
import pl.scoottrack.auth.service.AuthenticationService;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpointy do zarządzania rejestracją, logowaniem i aktywacją kont użytkowników")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Rejestracja użytkownika",
            description = "Rejestruje nowego użytkownika na podstawie przesłanych danych",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Użytkownik zarejestrowany pomyślnie"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe"),
                    @ApiResponse(responseCode = "409", description = "Użytkownik o podanym e-mailu już istnieje"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegistrationRequest request) {
        log.info("Registering user with e-mail: {}", request.getEmail());
        return authenticationService.register(request);
    }

    @Operation(
            summary = "Logowanie użytkownika",
            description = "Loguje użytkownika i zwraca token JWT w przypadku pomyślnej autoryzacji",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Użytkownik zalogowany pomyślnie"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe"),
                    @ApiResponse(responseCode = "401", description = "Niepoprawne dane logowania"),
                    @ApiResponse(responseCode = "403", description = "Konto użytkownika jest zablokowane lub nieaktywne"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        log.info("Authenticating user with e-mail: {}", request.getEmail());
        return authenticationService.authenticate(request);
    }

    @Operation(
            summary = "Aktywacja konta",
            description = "Aktywuje konto użytkownika na podstawie przesłanego tokenu aktywacyjnego",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Konto aktywowane pomyślnie"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono tokenu"),
                    @ApiResponse(responseCode = "400", description = "Token wygasł lub został użyty"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera")
            }
    )
    @GetMapping("/activate-account")
    public void activateAccount(@RequestParam String token) {
        log.info("Activating user account with token: {}", token);
        authenticationService.activateAccount(token);
    }
}
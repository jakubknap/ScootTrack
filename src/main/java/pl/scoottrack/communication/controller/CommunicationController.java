package pl.scoottrack.communication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.scoottrack.communication.service.CommunicationService;

@Log4j2
@RestController
@RequestMapping("/communication")
@RequiredArgsConstructor
@Tag(name = "Communication", description = "Endpointy do komunikacji zewnętrznej i wewnętrznej")
public class CommunicationController {

    private final CommunicationService communicationService;

    @Operation(
            summary = "Pobierz losową ciekawostkę o kotach",
            description = "Wywołuje zewnętrzny serwis API, aby pobrać losową ciekawostkę o kotach",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Losowa ciekawostka o kotach została pobrana"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera", content = @Content)
            }
    )
    @GetMapping("/random-facts")
    public String getRandomFactAboutCats() {
        log.info("Get random fact about cats");
        return communicationService.getRandomFactAboutCats();
    }

    @Operation(
            summary = "Wywołaj inny serwis",
            description = "Przykładowe wywołanie wewnętrznego serwisu w aplikacji lub innym module",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Odpowiedź z drugiego serwisu została uzyskana"),
                    @ApiResponse(responseCode = "500", description = "Błąd wewnętrzny serwera", content = @Content)
            }
    )
    @GetMapping("/service-b")
    public String callAnotherService() {
        log.info("Call another service");
        return communicationService.callAnotherService();
    }
}
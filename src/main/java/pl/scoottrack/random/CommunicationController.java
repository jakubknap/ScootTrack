package pl.scoottrack.random;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/communication")
@RequiredArgsConstructor
public class CommunicationController {

    private final CommunicationService communicationService;

    @GetMapping("/random-facts")
    public String getRandomFactAboutCats() {
        log.info("Get random fact about cats");
        return communicationService.getRandomFactAboutCats();
    }

    @GetMapping("/service-b")
    public String callAnotherService() {
        log.info("Call another service");
        return communicationService.callAnotherService();
    }
}
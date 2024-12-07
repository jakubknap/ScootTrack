package pl.scoottrack.communication;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Log4j2
@Service
public class CommunicationServiceImpl implements CommunicationService {

    private final RestTemplate restTemplate;

    public CommunicationServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String getRandomFactAboutCats() {
        final String uri = "https://catfact.ninja/fact";

        FactResponse response;

        try {
            response = restTemplate.getForObject(uri, FactResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(response)
                       .map(resp -> "Fact: \n" + resp.fact)
                       .orElseThrow(() -> {
                           log.error("Could not get random fact about cats");
                           return new RuntimeException("Nie można pobrać ciekawostki o kotach");
                       });
    }

    @Override
    public String callAnotherService() {
        final String uri = "http://localhost:8080/api";

        String response;

        try {
            response = restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(response)
                       .orElseThrow(() -> {
                           log.error("Could not call another service");
                           return new RuntimeException("Nie można uzyskać odpowiedzi z drugiego serwisu");
                       });
    }

    private record FactResponse(String fact, Integer length) {}
}
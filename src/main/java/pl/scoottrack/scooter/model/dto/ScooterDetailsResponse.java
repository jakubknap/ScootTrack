package pl.scoottrack.scooter.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ScooterDetailsResponse {

    private String name;
    private String brand;
    private String model;
    private String type;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;
    private String userFirstname;
    private UUID scooterUuid;
    private UUID userUuid;
}
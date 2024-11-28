package pl.scoottrack.scooter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScooterListResponse(String name,
                                  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
                                  LocalDateTime createdDate,
                                  UUID scooterUuid) {}
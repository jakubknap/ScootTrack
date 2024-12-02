package pl.scoottrack.repair;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RepairDetailsResponse(String title, String description, BigDecimal price, LocalDateTime createdDate, UUID scooterUuid, UUID repairUuid) {}
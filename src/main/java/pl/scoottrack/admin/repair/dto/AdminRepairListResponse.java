package pl.scoottrack.admin.repair.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AdminRepairListResponse(String title, String description, BigDecimal price, LocalDateTime createdDate, UUID scooterUuid, UUID repairUuid) {}
package pl.scoottrack.repair.model.dto;

import java.math.BigDecimal;

public record StatsResponse(int totalRepairs, BigDecimal totalCostsOfRepairs, BigDecimal averageCostsOfRepair) {}
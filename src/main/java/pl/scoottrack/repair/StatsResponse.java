package pl.scoottrack.repair;

import java.math.BigDecimal;

public record StatsResponse(int totalRepairs, BigDecimal totalCostsOfRepairs, BigDecimal averageCostsOfRepair) {}
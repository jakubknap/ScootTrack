package pl.scoottrack.admin.scooter;

import java.util.UUID;

public record AdminScooterListResponse(String name, String brand, String model, String type, UUID userUuid, String userFirstname, Integer countOfRepairs) {}

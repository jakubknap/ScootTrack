package pl.scoottrack.admin.scooter.dto;

import java.util.UUID;

public interface AdminScooterListProjection {
    String getName();

    String getBrand();

    String getModel();

    String getType();

    UUID getUserUuid();

    String getUserFirstname();

    Integer getCountOfRepairs();
}
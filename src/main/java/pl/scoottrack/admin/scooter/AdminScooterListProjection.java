package pl.scoottrack.admin.scooter;

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
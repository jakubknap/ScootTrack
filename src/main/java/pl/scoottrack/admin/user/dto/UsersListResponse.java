package pl.scoottrack.admin.user.dto;

import java.util.UUID;

public record UsersListResponse(UUID uuid, String email, String firstname, boolean enabled, boolean locked) {}
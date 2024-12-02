package pl.scoottrack.admin.user;

import java.util.UUID;

public record UsersListResponse(UUID uuid, String email, String firstname, boolean enabled, boolean locked) {}
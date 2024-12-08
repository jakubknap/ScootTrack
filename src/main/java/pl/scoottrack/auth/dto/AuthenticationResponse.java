package pl.scoottrack.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Reprezentuje odpowiedź z tokenem JWT po udanym logowaniu")
public record AuthenticationResponse(
        @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {}
package pl.scoottrack.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private final String secretKey = "ZXMKQBBFCLH0ZPAHWN4YGVEGER5NUKD7FGD2321SADA213123DASDGFGFDGBNHJHGE";
    private final long jwtExpiration = 8640000;
    private final String username = "jakub@scoottrack.pl";
    @Mock
    private UserDetails userDetails;
    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(secretKey, jwtExpiration);
    }

    @Test
    void shouldGenerateToken() {
        when(userDetails.getUsername()).thenReturn(username);

        String generatedToken = jwtService.generateToken(userDetails);

        assertNotNull(generatedToken);
        assertTrue(generatedToken.startsWith("eyJhbGciOiJIUzM4NCJ9"));
    }

    @Test
    void shouldExtractUsernameFromToken() {
        when(userDetails.getUsername()).thenReturn(username);
        String generatedToken = jwtService.generateToken(userDetails);

        String extractedUsername = jwtService.extractUsername(generatedToken);

        assertEquals(username, extractedUsername);
    }

    @Test
    void shouldGenerateTokenWithCustomClaims() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("customClaim", "customValue");

        when(userDetails.getUsername()).thenReturn(username);

        String tokenWithClaims = jwtService.generateToken(extraClaims, userDetails);

        assertNotNull(tokenWithClaims);
        assertTrue((jwtService.extractClaim(tokenWithClaims, a -> a.get("customClaim"))
                              .toString()
                              .contains("customValue")));
    }

    @Test
    void shouldHandleEmptyTokenGracefully() {
        String invalidToken = "";

        assertThrows(Exception.class, () -> jwtService.extractUsername(invalidToken));
    }
}
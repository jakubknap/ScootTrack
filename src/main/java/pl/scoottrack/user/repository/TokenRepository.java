package pl.scoottrack.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.scoottrack.user.model.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
}
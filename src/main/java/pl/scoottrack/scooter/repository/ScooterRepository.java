package pl.scoottrack.scooter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.scoottrack.scooter.model.Scooter;
import pl.scoottrack.scooter.model.dto.ScooterListResponse;

import java.util.Optional;
import java.util.UUID;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {

    Optional<Scooter> findByUuid(UUID uuid);

    @Query("""
            SELECT new pl.scoottrack.scooter.model.dto.ScooterListResponse(s.name, s.createdDate, s.uuid)
            FROM Scooter s
            JOIN FETCH User u ON u.uuid = s.user.uuid
            WHERE s.user.uuid = :uuid
            """)
    Page<ScooterListResponse> findAllByUserUuid(UUID uuid, Pageable pageable);
}
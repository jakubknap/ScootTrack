package pl.scoottrack.admin.scooter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.scoottrack.scooter.Scooter;

import java.util.Optional;
import java.util.UUID;

public interface AdminScooterRepository extends JpaRepository<Scooter, Long> {

    @Query(value = """
            SELECT s.name,
                   s.brand,
                   s.model,
                   s.type,
                   u.uuid      AS userUuid,
                   u.firstname AS userFirstname,
                   COUNT(r.id) AS countOfRepairs
            FROM scooter s
                     JOIN
                 _user u ON u.id = s.user_id
                     LEFT JOIN
                 repair r ON r.scooter_id = s.id
            GROUP BY s.name, s.brand, s.model, s.type, u.uuid, u.firstname
            """, nativeQuery = true)
    Page<AdminScooterListProjection> getAllScooters(Pageable pageable);

    Optional<Scooter> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}

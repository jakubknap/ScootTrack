package pl.scoottrack.repair.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.scoottrack.repair.model.Repair;
import pl.scoottrack.repair.model.dto.RepairDetailsResponse;
import pl.scoottrack.repair.model.dto.RepairListResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepairRepository extends JpaRepository<Repair, Long> {

    Optional<Repair> findByUuid(UUID uuid);

    @Query("""
            SELECT new pl.scoottrack.repair.model.dto.RepairListResponse(r.title, r.price, r.uuid, s.uuid)
            FROM Repair r
            JOIN FETCH Scooter s ON r.scooter.uuid = s.uuid
            WHERE r.userUuid = :userUuid
            """)
    Page<RepairListResponse> findAllByUserUuid(UUID userUuid, Pageable pageable);

    @Query("""
            SELECT new pl.scoottrack.repair.model.dto.RepairDetailsResponse(r.title, r.description, r.price, r.createdDate, r.scooter.uuid, r.uuid)
            FROM Repair r
            JOIN FETCH Scooter s ON r.scooter.uuid = s.uuid
            WHERE s.uuid = :scooterUuid
            """)
    List<RepairDetailsResponse> findAllRepairsByScooterUuid(UUID scooterUuid);

    @Query("""
            SELECT r.price
            FROM Repair r
            WHERE r.userUuid = :userUuid
            """)
    List<BigDecimal> findAllRepairCostsByUserUuid(UUID userUuid);
}
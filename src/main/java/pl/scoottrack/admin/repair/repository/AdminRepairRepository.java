package pl.scoottrack.admin.repair.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.scoottrack.admin.repair.dto.AdminRepairListResponse;
import pl.scoottrack.repair.model.Repair;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepairRepository extends JpaRepository<Repair, Long> {

    @Query("""
            SELECT new pl.scoottrack.admin.repair.dto.AdminRepairListResponse(r.title, r.description, r.price, r.createdDate, r.scooter.uuid, r.uuid)
            FROM Repair r
            """)
    Page<AdminRepairListResponse> findAllRepairs(Pageable pageable);

    Optional<Repair> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
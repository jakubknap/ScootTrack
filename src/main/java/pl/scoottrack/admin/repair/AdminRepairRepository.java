package pl.scoottrack.admin.repair;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.scoottrack.repair.Repair;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepairRepository extends JpaRepository<Repair, Long> {

    @Query("""
            SELECT new pl.scoottrack.admin.repair.AdminRepairListResponse(r.title, r.description, r.price, r.createdDate, r.scooter.uuid, r.uuid)
            FROM Repair r
            """)
    Page<AdminRepairListResponse> findAllRepairs(Pageable pageable);

    Optional<Repair> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
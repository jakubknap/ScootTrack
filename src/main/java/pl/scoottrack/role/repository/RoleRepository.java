package pl.scoottrack.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.scoottrack.role.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    @Query("""
            SELECT r
            FROM Role r
            WHERE r.name IN (:names)
            """)
    List<Role> findByName(List<String> names);
}
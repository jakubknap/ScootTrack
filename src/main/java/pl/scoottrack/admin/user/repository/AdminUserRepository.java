package pl.scoottrack.admin.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.scoottrack.admin.user.dto.UsersListResponse;
import pl.scoottrack.user.model.User;

import java.util.Optional;
import java.util.UUID;

public interface AdminUserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT new pl.scoottrack.admin.user.dto.UsersListResponse(u.uuid, u.email, u.firstname, u.enabled, u.accountLocked)
            FROM User u
            """)
    Page<UsersListResponse> findAllUsersList(Pageable pageable);

    Optional<User> findByUuid(UUID uuid);

    boolean existsByEmail(String email);

    void deleteUserByUuid(UUID uuid);
}
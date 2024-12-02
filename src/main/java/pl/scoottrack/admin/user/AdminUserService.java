package pl.scoottrack.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminUserService {

    Page<UsersListResponse> getAllUsers(Pageable pageable);

    void editUser(EditUserRequest request);

    void deleteUser(UUID uuid);
}
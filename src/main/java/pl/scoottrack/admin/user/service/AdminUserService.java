package pl.scoottrack.admin.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.scoottrack.admin.user.dto.EditUserRequest;
import pl.scoottrack.admin.user.dto.UsersListResponse;

import java.util.UUID;

public interface AdminUserService {

    Page<UsersListResponse> getAllUsers(Pageable pageable);

    void editUser(EditUserRequest request);

    void deleteUser(UUID uuid);
}
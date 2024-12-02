package pl.scoottrack.admin.user;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.scoottrack.role.Role;
import pl.scoottrack.role.RoleRepository;
import pl.scoottrack.user.User;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Page<UsersListResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAllUsersList(pageable);
    }

    @Override
    public void editUser(EditUserRequest request) {
        User user = getUser(request);

        String email = request.email();

        validateEmail(user, email);

        List<Role> roles = roleRepository.findByName(request.roles());
        validateRoles(roles, request);

        user.setFirstname(request.firstname());
        user.setEmail(email);
        user.setAccountLocked(request.accountLocked());
        user.setEnabled(request.enabled());
        user.setRoles(roles);

        userRepository.save(user);
        log.info("User edited successfully");
    }

    @Override
    public void deleteUser(UUID uuid) {
        userRepository.deleteUserByUuid(uuid);
        log.info("User deleted successfully");
    }

    private User getUser(EditUserRequest request) {
        return userRepository.findByUuid(request.uuid())
                             .orElseThrow(() -> {
                                 log.error("User with uuid: {} not found", request.uuid());
                                 return new EntityNotFoundException("Nie znaleziono użytkownika o podanym uuidzie");
                             });
    }

    private void validateEmail(User user, String email) {
        if (!user.getEmail()
                 .equals(email)) {
            if (userRepository.existsByEmail(email)) {
                log.error("User with email: {} already exists", email);
                throw new EntityExistsException("User with email: " + email + " already exists");
            }
        }
    }

    private void validateRoles(List<Role> roles, EditUserRequest request) {
        if (roles.isEmpty()) {
            log.error("Role not found. Entered roles: {}", request.roles());
            throw new EntityNotFoundException("Role not found");
        }
    }

}

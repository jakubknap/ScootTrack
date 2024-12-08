package pl.scoottrack.admin.user.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.scoottrack.admin.user.dto.EditUserRequest;
import pl.scoottrack.admin.user.dto.UsersListResponse;
import pl.scoottrack.admin.user.repository.AdminUserRepository;
import pl.scoottrack.admin.user.service.AdminUserService;
import pl.scoottrack.notification.service.NotificationService;
import pl.scoottrack.role.model.Role;
import pl.scoottrack.role.repository.RoleRepository;
import pl.scoottrack.user.model.User;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final NotificationService notificationService;

    @Override
    public Page<UsersListResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAllUsersList(pageable);
    }

    @Override
    public void editUser(EditUserRequest request) {
        User user = getUser(request.uuid());

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
        notificationService.sendNotification(user.getEmail(), "Twój profil został edytowany przez administratora");
        log.info("User edited successfully");
    }

    @Override
    public void deleteUser(UUID uuid) {
        User user = getUser(uuid);
        userRepository.deleteUserByUuid(uuid);
        notificationService.sendNotification(user.getEmail(), "Twoje konto zostało usunięte przez administratora");
        log.info("User deleted successfully");
    }

    private User getUser(UUID userUuid) {
        return userRepository.findByUuid(userUuid)
                             .orElseThrow(() -> {
                                 log.error("User with uuid: {} not found", userUuid);
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
package pl.scoottrack.admin.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Log4j2
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService userService;

    @GetMapping
    public Page<UsersListResponse> getAllUsers(@PageableDefault(size = 20, sort = "firstname", direction = DESC) Pageable pageable) {
        log.info("Get all users");
        return userService.getAllUsers(pageable);
    }

    @PutMapping
    public void editUser(@RequestBody @Valid EditUserRequest request) {
        log.info("Edit user with uuid: {}", request.uuid());
        userService.editUser(request);
    }

    @DeleteMapping("/{uuid}")
    public void deleteUser(@PathVariable String uuid) {
        log.info("Delete user with uuid: {}", uuid);
        userService.deleteUser(UUID.fromString(uuid));
    }
}
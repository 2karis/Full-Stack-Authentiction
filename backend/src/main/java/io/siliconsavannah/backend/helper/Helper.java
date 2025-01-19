package io.siliconsavannah.backend.helper;

import io.siliconsavannah.backend.enums.RoleName;
import io.siliconsavannah.backend.model.Role;
import io.siliconsavannah.backend.model.User;
import io.siliconsavannah.backend.repository.RoleRepository;
import io.siliconsavannah.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class Helper {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public User getAuthenticatedUser() {
        var authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authenticatedUser instanceof User userDetails) {
            return userDetails;
        }  else {
            throw new RuntimeException("Error occurred getting authenticated user");
        }
    }
    public Long getAuthenticatedUserIdParentId() {
        User authenticatedUser = getAuthenticatedUser();
        Role authenticatedUserRole = authenticatedUser.getRole();

        if(authenticatedUserRole.getName() == RoleName.ROLE_SUPER_ADMIN || authenticatedUserRole.getName() == RoleName.ROLE_OWNER) {
            return authenticatedUser.getId();
        } else {
            return authenticatedUser.getParentId();
        }
    }
}

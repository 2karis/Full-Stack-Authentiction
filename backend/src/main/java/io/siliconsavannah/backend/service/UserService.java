package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.configuration.PasswordEncoder;
import io.siliconsavannah.backend.dto.PasswordDto;
import io.siliconsavannah.backend.dto.SignUpDto;
import io.siliconsavannah.backend.dto.UserDto;
import io.siliconsavannah.backend.enums.RoleName;
import io.siliconsavannah.backend.helper.Helper;
import io.siliconsavannah.backend.model.PasswordResetToken;
import io.siliconsavannah.backend.model.Role;
import io.siliconsavannah.backend.model.User;
import io.siliconsavannah.backend.repository.PasswordResetTokenRepository;
import io.siliconsavannah.backend.repository.RoleRepository;
import io.siliconsavannah.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private static final int EXPIRATION = 60 * 24;

    private final Helper helper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public List<UserDto> readAllUsers(){
        return userRepository.findAll().stream().map(UserService::toDto).collect(Collectors.toList());
    }

    public void createUser(SignUpDto request){
        Long authenticatedUserIdParentId = helper.getAuthenticatedUserIdParentId();

        Role customerRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("Role not found!"));
        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encoder().encode(request.password()))
                .role(customerRole)
                .parentId(authenticatedUserIdParentId)
                .build();
        log.info("{}",user);

        userRepository.save(user);
    }
    public UserDto updateUserDetails(UserDto userDto) {

        User authenticatedUser = helper.getAuthenticatedUser();

        String email = authenticatedUser.getUsername();
        // Do something with the username
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (userDto.firstname() != null) user.setFirstname(userDto.firstname());
        if (userDto.lastname() != null) user.setLastname(userDto.lastname());
        if (userDto.email() != null) user.setEmail(userDto.email());

        User newUser = userRepository.save(user);

        return toDto(newUser);
    }

    public String updateUserPassword(PasswordDto passwordDto) {
        User authenticatedUser = helper.getAuthenticatedUser();
        String email = authenticatedUser.getUsername();
        // Do something with the username
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encoder().encode(passwordDto.password()));
        User updatedUser = userRepository.save(user);
        return "User Password Updated Successfully";
    }
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }


    public boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findFirstByEmail(email)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
            }
        };
    }

    private static UserDto toDto(User user){
        return new UserDto(user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole().getName().name());
    }

    private static User toEntity(UserDto userDto){
        return User.builder()
                .firstname(userDto.firstname())
                .lastname(userDto.lastname())
                .email(userDto.email())
                .build();
    }
}

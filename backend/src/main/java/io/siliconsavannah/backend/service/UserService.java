package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.dto.PasswordDto;
import io.siliconsavannah.backend.dto.SignUpDto;
import io.siliconsavannah.backend.dto.UserDto;
import io.siliconsavannah.backend.enums.RoleName;
import io.siliconsavannah.backend.model.Role;
import io.siliconsavannah.backend.model.User;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    //@Autowired
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDto> readAllUsers(){
        return userRepository.findAll().stream().map(UserService::toDto).collect(Collectors.toList());
    }

    public void createUser(SignUpDto request){

        Role customerRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("Role not found!"));
        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(customerRole)
                .build();
        log.info("{}",user);

        userRepository.save(user);
    }
    public UserDto updateUserDetails(UserDto userDto) {

        var authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authenticatedUser instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authenticatedUser;
            String email = userDetails.getUsername();
            // Do something with the username
            User user = userRepository.findFirstByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found!"));

            if (userDto.firstname() != null) user.setFirstname(userDto.firstname());
            if (userDto.lastname() != null) user.setLastname(userDto.lastname());
            if (userDto.email() != null) user.setEmail(userDto.email());

            User newUser = userRepository.save(user);

            return toDto(newUser);
        }else{
            throw new RuntimeException("Error occurred updating user details");
        }
    }

    public String updateUserPassword(PasswordDto passwordDto) {
        var authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authenticatedUser instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authenticatedUser;
            String email = userDetails.getUsername();
            // Do something with the username
            User user = userRepository.findFirstByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setPassword(passwordEncoder.encode(passwordDto.password()));
            User updatedUser = userRepository.save(user);
            return "User Password Updated Successfully";
        }else{
            throw new RuntimeException("Error occurred updating password");
        }
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

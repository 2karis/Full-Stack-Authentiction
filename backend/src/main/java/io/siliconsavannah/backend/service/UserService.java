package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.dto.PasswordDto;
import io.siliconsavannah.backend.dto.SignUpDto;
import io.siliconsavannah.backend.dto.UserDto;
import io.siliconsavannah.backend.enums.Role;
import io.siliconsavannah.backend.model.User;
import io.siliconsavannah.backend.repository.AuthoritiesRepository;
import io.siliconsavannah.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDto> readAllUsers(){
        return userRepository.findAll().stream().map(entity-> UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .build()).collect(Collectors.toList());
    }

    public void createUser(SignUpDto request){

        //Authorities authority = authoritiesRepository.findById(1).orElseThrow();
//        //authoritiesRepository.save(authority);
        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER.name())
                .build();
        log.info("{}",user);

        userRepository.save(user);
        //return new UsersDto(newUser.getId(), newUser.getFirstname(), newUser.getLastname(), newUser.getUsername(), newUser.getAuthorities());
    }
    public UserDto getUser(SignUpDto request) {
//        Authorities authority = Authorities.builder()
//                .authority(Role.USER.name())
//                .build();
//        authoritiesRepository.save(authority);

        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER.name())
                .build();
        User newUser = userRepository.save(user);

        return new UserDto(newUser.getId(), newUser.getFirstname(), newUser.getLastname(), newUser.getEmail(), newUser.getRole());
    }
    public UserDto updateUserDetails(UserDto userDto) {

        User user = User.builder()
                .firstname(userDto.firstname())
                .lastname(userDto.lastname())
                .email(userDto.email())
                .role(Role.USER.name())
                .build();
        User newUser = userRepository.save(user);

        return new UserDto(newUser.getId(), newUser.getFirstname(), newUser.getLastname(), newUser.getEmail(), newUser.getRole());
    }

    public UserDto updateUserPassword(PasswordDto passwordDto) {
        User user = User.builder()
                .password(passwordEncoder.encode(passwordDto.password()))
                .build();
        User newUser = userRepository.save(user);

        return new UserDto(newUser.getId(), newUser.getFirstname(), newUser.getLastname(), newUser.getEmail(), newUser.getRole());
    }
    public void deleteUser(UserDto user) {
        userRepository.deleteById(user.id());
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
}

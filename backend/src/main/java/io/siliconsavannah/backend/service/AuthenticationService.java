package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.configuration.PasswordEncoder;
import io.siliconsavannah.backend.dto.AuthDto;
import io.siliconsavannah.backend.dto.LoginDto;
import io.siliconsavannah.backend.dto.SignUpDto;
import io.siliconsavannah.backend.enums.RoleName;
import io.siliconsavannah.backend.model.PasswordResetToken;
import io.siliconsavannah.backend.model.Role;
import io.siliconsavannah.backend.model.User;
import io.siliconsavannah.backend.repository.PasswordResetTokenRepository;
import io.siliconsavannah.backend.repository.RoleRepository;
import io.siliconsavannah.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired

    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String signUp(SignUpDto request) throws Exception {
        if(userService.hasUserWithEmail(request.email())){
            throw new Exception("User already Exists");
        }else{

            Role customerRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("Role not found!"));
            User user = User.builder()
                    .firstname(request.firstname())
                    .lastname(request.lastname())
                    .email(request.email())
                    .password(passwordEncoder.encoder().encode(request.password()))
                    .role(customerRole)
                    .parentId(1L)
                    .build();
            log.info("{}",user);

            userRepository.save(user);
            return "User Created Successfully";
        }

    }
    public AuthDto login(LoginDto request)throws
            BadCredentialsException,
            DisabledException,
            UsernameNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),
                    request.password()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect email or password.");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.email());
        final String jwt = jwtService.generateToken(userDetails);
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
            return AuthDto.builder()
                    .email(userDetails.getUsername())
                    .token(jwt)
                    .build();
   }



    public String passwordReset(String email) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = createPasswordResetTokenForUser(user, token);
        emailSenderService.sendPasswordResetEmail(passwordResetToken, user);
        return "Password Reset Email Sent Successfully";
    }

    public PasswordResetToken createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = PasswordResetToken.builder()
                .token(token)
                .userId(user.getId())

                .build();
        return passwordResetTokenRepository.save(myToken);
    }
}

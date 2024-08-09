package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.dto.AuthDto;
import io.siliconsavannah.backend.dto.LoginDto;
import io.siliconsavannah.backend.dto.SignUpDto;
import io.siliconsavannah.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  AuthenticationManager authenticationManager;

    //    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private AuthenticationManager authenticationManager;

    public AuthDto signUp(SignUpDto request) throws Exception {
        if(userService.hasUserWithEmail(request.email())){
            throw new Exception("User already Exists");
        }else{
            userService.createUser(request);

//        String basicToken = this.generateBasicToken(request.getUsername(), request.getPassword());
//        return AuthenticationResponse.builder()
//                .token(basicToken)
//                .build();

//    String generateBasicToken(String username, String password){
//        String encoding = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
//        return "Basic " + encoding;
            return null;
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
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
            return AuthDto.builder()
                    .email(userDetails.getUsername())
                    .role(roles.getFirst())
                    .token(jwt)
                    .build();
   }

//    public static boolean hasRole (String roleName)
//    {
//        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
//    }
}

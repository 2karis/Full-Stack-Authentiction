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

import java.util.List;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;


    public String signUp(SignUpDto request) throws Exception {
        if(userService.hasUserWithEmail(request.email())){
            throw new Exception("User already Exists");
        }else{
            userService.createUser(request);
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

package io.siliconsavannah.backend.controller;

import io.siliconsavannah.backend.dto.LoginDto;
import io.siliconsavannah.backend.dto.SignUpDto;
import io.siliconsavannah.backend.repository.UserRepository;
import io.siliconsavannah.backend.service.AuthenticationService;
import io.siliconsavannah.backend.service.JwtService;
import io.siliconsavannah.backend.service.RoleService;
import io.siliconsavannah.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignUpDto request){
        try{
            return ResponseEntity.status(201).body(authenticationService.signUp(request));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("failed to Sign up new User "+e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request){
        try{
            return ResponseEntity.ok(authenticationService.login(request));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("failed to login User "+e.getMessage());
        }
    }
}

package io.siliconsavannah.backend.controller;

import io.siliconsavannah.backend.dto.LoginDto;
import io.siliconsavannah.backend.dto.SignUpDto;
import io.siliconsavannah.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
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

    @GetMapping("/currentTime")
    public ResponseEntity<String> getCurrentTime(){
        return ResponseEntity.ok(String.valueOf(System.currentTimeMillis()));
    }

}

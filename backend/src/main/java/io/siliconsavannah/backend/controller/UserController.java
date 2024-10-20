package io.siliconsavannah.backend.controller;


import io.siliconsavannah.backend.dto.UserDto;
import io.siliconsavannah.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping("/readall")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.readAllUsers(), HttpStatus.OK);
    }

}
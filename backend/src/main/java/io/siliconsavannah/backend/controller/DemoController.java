package io.siliconsavannah.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoController {
    @PreAuthorize("hasAuthority('SHOW_MAINTENANCE_REQUEST')")
    @RequestMapping("/hello")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello World!");
    }
}

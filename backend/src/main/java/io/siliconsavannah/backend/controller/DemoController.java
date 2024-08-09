package io.siliconsavannah.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/demo")
public class DemoController {
    @RequestMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello World!");
    }
}

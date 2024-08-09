package io.siliconsavannah.backend;

import io.siliconsavannah.backend.service.AuthenticationService;
import io.siliconsavannah.backend.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
@Slf4j
@SpringBootApplication
public class BackendApplication{
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}

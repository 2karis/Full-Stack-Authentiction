package io.siliconsavannah.backend.configuration;

import io.siliconsavannah.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
@RequiredArgsConstructor
@Configuration
public class RoleSeeder implements ApplicationRunner {
    private final RoleService roleService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = roleService.RoleCount();
        if(count == 0L) roleService.seedRoles();
    }
}

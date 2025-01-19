package io.siliconsavannah.backend.configuration;

import io.siliconsavannah.backend.service.DatabaseSeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
@RequiredArgsConstructor
@Configuration
public class DataBaseSeedConfiguration implements ApplicationRunner {
    private final DatabaseSeedService databaseSeedService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        databaseSeedService.seedRolesAndAuthorities();
        databaseSeedService.seedUsers();
    }
}

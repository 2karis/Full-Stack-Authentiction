package io.siliconsavannah.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.siliconsavannah.backend.dto.UserDto;
import io.siliconsavannah.backend.dto.UserSeedDto;
import io.siliconsavannah.backend.enums.RoleName;
import io.siliconsavannah.backend.model.Authority;
import io.siliconsavannah.backend.model.Role;
import io.siliconsavannah.backend.model.RoleAuthority;
import io.siliconsavannah.backend.model.User;
import io.siliconsavannah.backend.repository.AuthorityRepository;
import io.siliconsavannah.backend.repository.RoleRepository;
import io.siliconsavannah.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DatabaseSeedService {
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    @Value("${seed.data.path}")
    private String jsonFilePath;

    public void seedRolesAndAuthorities() throws IOException {
        long roleCount = roleRepository.count();
        long authorityCount = authorityRepository.count();
        if (roleCount > 0L && authorityCount > 0L){
            return;
        }
        try {
            // Read JSON file
            File jsonFile = new File(jsonFilePath);
            JsonNode rootNode = objectMapper.readTree(jsonFile);
            if (rootNode.has("roles") && rootNode.has("authorities") && rootNode.has("role_authorities")) {
                List<Role> roles = objectMapper.readValue(
                        rootNode.get("roles").toString(),
                        new TypeReference<>() {
                        }
                );

                List<Authority> authorities = objectMapper.readValue(
                        rootNode.get("authorities").toString(),
                        new TypeReference<>() {
                        }
                );


                List<RoleAuthority> roleAuthorities = objectMapper.readValue(
                        rootNode.get("role_authorities").toString(),
                        new TypeReference<>() {
                        }
                );
                authorityRepository.saveAll(authorities);
                roleRepository.saveAll(addAuthorityToRole(roles, authorities, roleAuthorities));
                log.info("Loaded {} roles", roles.size());
            }else{
                log.info("Seed data not found");
            }

        } catch (IOException e) {
            log.error("Error loading data from JSON: {}", e.getMessage());
            throw e;
        }
    }

    public List<Role> addAuthorityToRole(List<Role> roles , List<Authority> authorities, List<RoleAuthority> roleAuthorities){
        HashMap<Long, Role> roleMap = new HashMap<>();
        HashMap<Long, Authority> authorityMap = new HashMap<>();

        for(Role role : roles){
            role.setAuthorities(new HashSet<>());
            roleMap.put(role.getId(), role);
        }

        for(Authority authority : authorities)
            authorityMap.put(authority.getId(), authority);

        for(RoleAuthority roleAuthority : roleAuthorities){
            Set<Authority> authoritySet = roleMap.get(roleAuthority.getRoleId()).getAuthorities();

                roleMap.get(roleAuthority.getRoleId()).getAuthorities().add(authorityMap.get(roleAuthority.getAuthorityId()));
        }
        return roles;
    }

    public long roleCount(){
        return roleRepository.count();
    }

    public void seedUsers() throws IOException {
        long count = userRepository.count();
        if (count > 0L) return;
        try {
            // Read JSON file
            File jsonFile = new File(jsonFilePath);
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            if (rootNode.has("Users")) {
                List<UserSeedDto> users = objectMapper.readValue(
                        rootNode.get("Users").toString(),
                        new TypeReference<>() {
                        }
                );
                List<User> userEntities = users.stream().map(this::toEntity).toList();
                userRepository.saveAll(userEntities);
                log.info("Loaded {} users", users.size());
            } else {
                log.info("Seed data not found");
            }
        }catch (IOException e) {
            log.error("Error loading data from JSON: {}", e.getMessage());
            throw e;
        }
    }

    private User toEntity(UserSeedDto userDto){
        Role customerRole = roleRepository.findByName(userDto.getRole()).orElseThrow(() -> new RuntimeException("Role not found!"));

        return User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(customerRole)
                .build();
    }
}

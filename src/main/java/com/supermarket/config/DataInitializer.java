package com.supermarket.config;

import com.supermarket.domain.Role;
import com.supermarket.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initRoles() {
        return args -> {
            createRoleIfMissing("ADMIN");
            createRoleIfMissing("CASHIER");
            createRoleIfMissing("MANAGER");
        };
    }

    private void createRoleIfMissing(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            log.info("Seeding default role: {}", roleName);
            return roleRepository.save(Role.builder().name(roleName).build());
        });
    }
}

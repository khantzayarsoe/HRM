// config/DataInitializer.java
package com.humanresource.humanresourcemanagement.config;

import com.humanresource.humanresourcemanagement.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        // Initialize default permissions
        authService.initDefaultPermissions();

        // Initialize default super admin
        authService.initDefaultSuperAdmin();
    }
}
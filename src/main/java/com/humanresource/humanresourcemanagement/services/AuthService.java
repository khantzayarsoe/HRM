// services/AuthService.java
package com.humanresource.humanresourcemanagement.services;

import com.humanresource.humanresourcemanagement.dto.CreateSubAdminDTO;
import com.humanresource.humanresourcemanagement.dto.LoginRequestDTO;
import com.humanresource.humanresourcemanagement.dto.LoginResponseDTO;
import com.humanresource.humanresourcemanagement.dto.PermissionDTO;
import com.humanresource.humanresourcemanagement.entities.Admin;
import com.humanresource.humanresourcemanagement.entities.Permission;
import com.humanresource.humanresourcemanagement.entities.Role;
import com.humanresource.humanresourcemanagement.repositories.AdminRepository;
import com.humanresource.humanresourcemanagement.repositories.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AdminRepository adminRepository;
    private final PermissionRepository permissionRepository;

    // Initialize default super admin
    @Transactional
    public void initDefaultSuperAdmin() {
        if (!adminRepository.existsByUsername("superadmin")) {
            Admin superAdmin = Admin.builder()
                    .username("superadmin")
                    .password("admin123")
                    .fullName("Super Administrator")
                    .email("superadmin@gmail.com")
                    .role(Role.SUPER_ADMIN)
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .build();

            superAdmin.hashPassword();
            adminRepository.save(superAdmin);
            log.info("Default super admin created");
        }
    }

    // Initialize default permissions
    @Transactional
    public void initDefaultPermissions() {
        if (permissionRepository.count() == 0) {
            createPermission("MANAGE_EMPLOYEES", "Can manage employees");
            createPermission("MANAGE_DEPARTMENTS", "Can manage departments");
            createPermission("MANAGE_POSITIONS", "Can manage positions");
            createPermission("MANAGE_ATTENDANCE", "Can manage attendance");
            createPermission("MANAGE_PAYROLL", "Can manage payroll");
            createPermission("VIEW_RESIGN", "Can view resign");
            log.info("Default permissions created");
        }
    }

    private void createPermission(String name, String description) {
        Permission permission = Permission.builder()
                .name(name)
                .description(description)
                .build();
        permissionRepository.save(permission);
    }

    // Login method
    public LoginResponseDTO login(LoginRequestDTO request) {
        log.info("Login attempt for username: {}", request.getUsername());

        // Find admin by username
        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        // Verify password
        if (!admin.verifyPassword(request.getPassword())) {
            throw new RuntimeException("Invalid  password");
        }

        // Check if account is active
        if (!admin.getIsActive()) {
            throw new RuntimeException("Account is deactivated");
        }

        // Update last login time
        admin.setLastLoginAt(LocalDateTime.now());
        adminRepository.save(admin);

        // Build response
        LoginResponseDTO response = LoginResponseDTO.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .role(admin.getRole().toString())
                .active(admin.getIsActive())
                .build();

        // Add permissions for sub-admin
        if (admin.getRole() == Role.SUB_ADMIN && admin.getPermissions() != null) {
            List<String> permissionNames = admin.getPermissions().stream()
                    .map(Permission::getName)
                    .collect(Collectors.toList());
            response.setPermissions(permissionNames);
        }

        log.info("Login successful for user: {} with role: {}", admin.getUsername(), admin.getRole());
        return response;
    }

    // Create sub admin (only super admin can do this)
    @Transactional
    public LoginResponseDTO createSubAdmin(CreateSubAdminDTO dto, Long superAdminId) {
        // Verify super admin exists
        Admin superAdmin = adminRepository.findById(superAdminId)
                .orElseThrow(() -> new RuntimeException("Super admin not found"));

        if (superAdmin.getRole() != Role.SUPER_ADMIN) {
            throw new RuntimeException("Only super admin can create sub admins");
        }

        // Check if username already exists
        if (adminRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Get permissions
        List<Permission> permissions = permissionRepository.findByIdIn(dto.getPermissionIds());

        // Create sub admin
        Admin subAdmin = Admin.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .role(Role.SUB_ADMIN)
                .isActive(true)
                .permissions(permissions)
                .createdAt(LocalDateTime.now())
                .build();

        subAdmin.hashPassword();
        adminRepository.save(subAdmin);

        log.info("Sub admin created: {} by super admin: {}", subAdmin.getUsername(), superAdmin.getUsername());

        return LoginResponseDTO.builder()
                .id(subAdmin.getId())
                .username(subAdmin.getUsername())
                .fullName(subAdmin.getFullName())
                .email(subAdmin.getEmail())
                .role(subAdmin.getRole().toString())
                .active(subAdmin.getIsActive())
                .permissions(permissions.stream().map(Permission::getName).collect(Collectors.toList()))
                .build();
    }

    // Get all permissions
    public List<PermissionDTO> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(p -> PermissionDTO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    // Get all sub admins
    public List<LoginResponseDTO> getAllSubAdmins() {
        return adminRepository.findByRole(Role.SUB_ADMIN).stream()
                .map(admin -> LoginResponseDTO.builder()
                        .id(admin.getId())
                        .username(admin.getUsername())
                        .fullName(admin.getFullName())
                        .email(admin.getEmail())
                        .role(admin.getRole().toString())
                        .active(admin.getIsActive())
                        .permissions(admin.getPermissions() != null ?
                                admin.getPermissions().stream().map(Permission::getName).collect(Collectors.toList()) :
                                null)
                        .build())
                .collect(Collectors.toList());
    }

    // Toggle admin status (activate/deactivate)
    @Transactional
    public void toggleAdminStatus(Long adminId, Long superAdminId) {
        Admin superAdmin = adminRepository.findById(superAdminId)
                .orElseThrow(() -> new RuntimeException("Super admin not found"));

        if (superAdmin.getRole() != Role.SUPER_ADMIN) {
            throw new RuntimeException("Only super admin can change admin status");
        }

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setIsActive(!admin.getIsActive());
        adminRepository.save(admin);

        log.info("Admin {} status toggled to {} by super admin {}",
                admin.getUsername(), admin.getIsActive(), superAdmin.getUsername());
    }

    // Delete sub admin
    @Transactional
    public void deleteSubAdmin(Long adminId, Long superAdminId) {
        Admin superAdmin = adminRepository.findById(superAdminId)
                .orElseThrow(() -> new RuntimeException("Super admin not found"));

        if (superAdmin.getRole() != Role.SUPER_ADMIN) {
            throw new RuntimeException("Only super admin can delete sub admins");
        }

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (admin.getRole() != Role.SUB_ADMIN) {
            throw new RuntimeException("Can only delete sub admins");
        }

        adminRepository.delete(admin);
        log.info("Sub admin {} deleted by super admin {}", admin.getUsername(), superAdmin.getUsername());
    }


    // services/AuthService.java (add this method to your existing service)

    @Transactional
    public LoginResponseDTO updateSubAdminPermissions(Long adminId, List<Long> permissionIds, Long superAdminId) {
        // Verify super admin exists
        Admin superAdmin = adminRepository.findById(superAdminId)
                .orElseThrow(() -> new RuntimeException("Super admin not found"));

        if (superAdmin.getRole() != Role.SUPER_ADMIN) {
            throw new RuntimeException("Only super admin can update sub admin permissions");
        }

        // Find the sub admin
        Admin subAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Sub admin not found"));

        if (subAdmin.getRole() != Role.SUB_ADMIN) {
            throw new RuntimeException("Can only update sub admin permissions");
        }

        // Get the new permissions
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);

        // Update the sub admin's permissions
        subAdmin.setPermissions(permissions);
//        subAdmin.setUpdatedAt(LocalDateTime.now());
        adminRepository.save(subAdmin);

        log.info("Permissions updated for sub admin: {} by super admin: {}",
                subAdmin.getUsername(), superAdmin.getUsername());

        // Return updated sub admin data
        return LoginResponseDTO.builder()
                .id(subAdmin.getId())
                .username(subAdmin.getUsername())
                .fullName(subAdmin.getFullName())
                .email(subAdmin.getEmail())
                .role(subAdmin.getRole().toString())
                .active(subAdmin.getIsActive())
                .permissions(permissions.stream().map(Permission::getName).collect(Collectors.toList()))
                .build();
    }
}
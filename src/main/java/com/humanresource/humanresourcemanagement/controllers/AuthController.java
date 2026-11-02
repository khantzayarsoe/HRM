// controllers/AuthController.java
package com.humanresource.humanresourcemanagement.controllers;

import com.humanresource.humanresourcemanagement.dto.CreateSubAdminDTO;
import com.humanresource.humanresourcemanagement.dto.LoginRequestDTO;
import com.humanresource.humanresourcemanagement.dto.LoginResponseDTO;
import com.humanresource.humanresourcemanagement.dto.PermissionDTO;
import com.humanresource.humanresourcemanagement.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // Create sub admin (requires super admin ID in header)
    @PostMapping("/subadmins")
    public ResponseEntity<LoginResponseDTO> createSubAdmin(
            @RequestBody CreateSubAdminDTO dto,
            @RequestHeader("X-Super-Admin-ID") Long superAdminId) {
        LoginResponseDTO response = authService.createSubAdmin(dto, superAdminId);
        return ResponseEntity.ok(response);
    }

    // Get all permissions
    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        return ResponseEntity.ok(authService.getAllPermissions());
    }

    // Get all sub admins
    @GetMapping("/subadmins")
    public ResponseEntity<List<LoginResponseDTO>> getAllSubAdmins(
            @RequestHeader("X-Super-Admin-ID") Long superAdminId) {
        // You might want to verify super admin here
        return ResponseEntity.ok(authService.getAllSubAdmins());
    }

    // Toggle admin status
    @PutMapping("/subadmins/{adminId}/toggle")
    public ResponseEntity<Void> toggleAdminStatus(
            @PathVariable Long adminId,
            @RequestHeader("X-Super-Admin-ID") Long superAdminId) {
        authService.toggleAdminStatus(adminId, superAdminId);
        return ResponseEntity.ok().build();
    }

    // Delete sub admin
    @DeleteMapping("/subadmins/{adminId}")
    public ResponseEntity<Void> deleteSubAdmin(
            @PathVariable Long adminId,
            @RequestHeader("X-Super-Admin-ID") Long superAdminId) {
        authService.deleteSubAdmin(adminId, superAdminId);
        return ResponseEntity.ok().build();
    }

    // Update sub admin permissions
    @PutMapping("/subadmins/{adminId}/permissions")
    public ResponseEntity<LoginResponseDTO> updateSubAdminPermissions(
            @PathVariable Long adminId,
            @RequestBody List<Long> permissionIds,
            @RequestHeader("X-Super-Admin-ID") Long superAdminId) {
        LoginResponseDTO response = authService.updateSubAdminPermissions(adminId, permissionIds, superAdminId);
        return ResponseEntity.ok(response);
    }
}


//
//// controllers/AuthController.java
//package com.humanresource.humanresourcemanagement.controllers;
//
//import com.humanresource.humanresourcemanagement.dto.CreateSubAdminDTO;
//import com.humanresource.humanresourcemanagement.dto.LoginRequestDTO;
//import com.humanresource.humanresourcemanagement.dto.LoginResponseDTO;
//import com.humanresource.humanresourcemanagement.dto.PermissionDTO;
//import com.humanresource.humanresourcemanagement.services.AuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:5173")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthService authService;
//
//    // Login endpoint
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
//        LoginResponseDTO response = authService.login(request);
//        return ResponseEntity.ok(response);
//    }
//
//    // Create sub admin (requires super admin ID in header)
//    @PostMapping("/subadmins")
//    public ResponseEntity<LoginResponseDTO> createSubAdmin(
//            @RequestBody CreateSubAdminDTO dto,
//            @RequestHeader("X-Super-Admin-ID") Long superAdminId) {
//        LoginResponseDTO response = authService.createSubAdmin(dto, superAdminId);
//        return ResponseEntity.ok(response);
//    }
//
//    // Update sub admin permissions
//    @PutMapping("/subadmins/{adminId}/permissions")
//    public ResponseEntity<LoginResponseDTO> updateSubAdminPermissions(
//            @PathVariable Long adminId,
//            @RequestBody List<Long> permissionIds,
//            @RequestHeader("X-Super-Admin-ID") Long superAdminId) {
//        LoginResponseDTO response = authService.updateSubAdminPermissions(adminId, permissionIds, superAdminId);
//        return ResponseEntity.ok(response);
//    }
//
//    // Get all permissions
//    @GetMapping("/permissions")
//    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
//        return ResponseEntity.ok(authService.getAllPermissions());
//    }
//
//    // Get all sub admins
//    @GetMapping("/subadmins")
//    public ResponseEntity<List<LoginResponseDTO>> getAllSubAdmins(
//            @RequestHeader("X-Super-Admin-ID") Long superAdminId) {
//        return ResponseEntity.ok(authService.getAllSubAdmins());
//    }
//
//    // Toggle admin status
//    @PutMapping("/subadmins/{adminId}/toggle")
//    public ResponseEntity<Void> toggleAdminStatus(
//            @PathVariable Long adminId,
//            @RequestHeader("X-Super-Admin-ID") Long superAdminId) {
//        authService.toggleAdminStatus(adminId, superAdminId);
//        return ResponseEntity.ok().build();
//    }
//
//    // Delete sub admin
//    @DeleteMapping("/subadmins/{adminId}")
//    public ResponseEntity<Void> deleteSubAdmin(
//            @PathVariable Long adminId,
//            @RequestHeader("X-Super-Admin-ID") Long superAdminId) {
//        authService.deleteSubAdmin(adminId, superAdminId);
//        return ResponseEntity.ok().build();
//    }
//}
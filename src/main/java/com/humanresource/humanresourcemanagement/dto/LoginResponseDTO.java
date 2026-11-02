// dto/LoginResponseDTO.java
package com.humanresource.humanresourcemanagement.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class LoginResponseDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String role;  // SUPER_ADMIN or SUB_ADMIN
    private boolean active;
    private List<String> permissions; // For sub-admin permissions
    private String message;
}
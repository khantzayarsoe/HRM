// dto/CreateAdminDTO.java
package com.humanresource.humanresourcemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdminDTO {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private Map<String, List<String>> permissions; // Scope -> List of permissions
}
// dto/CreateSubAdminDTO.java
package com.humanresource.humanresourcemanagement.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateSubAdminDTO {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private List<Long> permissionIds; // IDs of permissions to grant
}
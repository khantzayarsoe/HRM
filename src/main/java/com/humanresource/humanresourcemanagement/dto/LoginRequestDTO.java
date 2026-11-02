// dto/LoginRequestDTO.java
package com.humanresource.humanresourcemanagement.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}
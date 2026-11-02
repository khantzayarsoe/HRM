package com.humanresource.humanresourcemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//not use
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {

    private String username;

    private String email;

    private String password;

}

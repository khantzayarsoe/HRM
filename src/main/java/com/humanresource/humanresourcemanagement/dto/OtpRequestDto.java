package com.humanresource.humanresourcemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//not use
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpRequestDto {
    private String email;
}
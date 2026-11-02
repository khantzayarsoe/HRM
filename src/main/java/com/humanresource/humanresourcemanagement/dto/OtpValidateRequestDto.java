package com.humanresource.humanresourcemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//not use
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidateRequestDto {
    private String email;
    private String otpCode;
}
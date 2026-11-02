package com.humanresource.humanresourcemanagement.controllers;

import com.humanresource.humanresourcemanagement.dto.ApiResponse;
import com.humanresource.humanresourcemanagement.dto.OtpRequestDto;
import com.humanresource.humanresourcemanagement.dto.OtpValidateRequestDto;
import com.humanresource.humanresourcemanagement.exceptions.EmailServiceErrorException;
import com.humanresource.humanresourcemanagement.exceptions.ExpiredOtpCodeException;
import com.humanresource.humanresourcemanagement.exceptions.InvalidOtpCodeException;
import com.humanresource.humanresourcemanagement.repositories.UserRepository;
import com.humanresource.humanresourcemanagement.services.EmailService;
import com.humanresource.humanresourcemanagement.services.OtpService;
import com.humanresource.humanresourcemanagement.utils.EmailDetails;
import com.humanresource.humanresourcemanagement.utils.OtpUtils;
import com.humanresource.humanresourcemanagement.utils.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/email")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @ExceptionHandler(InvalidOtpCodeException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidOtpCodeException(HttpServletRequest request, InvalidOtpCodeException e){

        var response = ResponseBuilder.error(HttpStatus.BAD_REQUEST.value(), "Otp code is invalid", request);
        return ResponseEntity.of(Optional.of(response));
    }

    @ExceptionHandler(ExpiredOtpCodeException.class)
    public ResponseEntity<ApiResponse> handleExpiredOtpCodeException(HttpServletRequest request, ExpiredOtpCodeException e){

        var response = ResponseBuilder.error(HttpStatus.BAD_REQUEST.value(), "Otp code is expired", request);
        return ResponseEntity.of(Optional.of(response));
    }


    @PostMapping("/otp/generate")
    public ResponseEntity<String> getOtpCode(@RequestBody OtpRequestDto otpRequestDto) throws EmailServiceErrorException {
        int otpCode = OtpUtils.generateOtp();
        otpService.associateOtpWithEmail(otpRequestDto.getEmail(), otpCode);

        var emailDetails = new EmailDetails();
        emailDetails.setRecipient(otpRequestDto.getEmail());
        emailDetails.setSubject("OTP test");
        emailDetails.setMsgBody(String.valueOf(otpCode));

        emailService.sendSimpleMail(emailDetails);
        return ResponseEntity.status(HttpStatus.OK).body("Otp sent successfully");
    }

    @PostMapping("/otp/validate")
    public ResponseEntity<String> validateOtp(@RequestBody OtpValidateRequestDto otpValidateRequestDto){
        try {
            var isValid= otpService.validateOtpCode(otpValidateRequestDto.getEmail(), Integer.parseInt(otpValidateRequestDto.getOtpCode()));

            if(isValid){
                userRepository.setEmailVerifiedToTrue(otpValidateRequestDto.getEmail());
                return ResponseEntity.status(HttpStatus.OK).body("Otp confirmed");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Otp is invalid");



        } catch (InvalidOtpCodeException e) {
            throw new RuntimeException(e);
        }
        catch (ExpiredOtpCodeException e){
            throw new RuntimeException(e);
        }

        catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}

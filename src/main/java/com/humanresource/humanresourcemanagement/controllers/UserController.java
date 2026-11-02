package com.humanresource.humanresourcemanagement.controllers;

import com.humanresource.humanresourcemanagement.dto.UserRegisterRequestDto;
import com.humanresource.humanresourcemanagement.entities.User;
import com.humanresource.humanresourcemanagement.exceptions.EmailServiceErrorException;
import com.humanresource.humanresourcemanagement.repositories.UserRepository;
import com.humanresource.humanresourcemanagement.services.EmailService;
import com.humanresource.humanresourcemanagement.services.OtpService;
import com.humanresource.humanresourcemanagement.services.PasswordEncoder;
import com.humanresource.humanresourcemanagement.utils.EmailDetails;
import com.humanresource.humanresourcemanagement.utils.OtpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OtpService otpService;


    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/api/auth/user/register")
    public User createUser(@RequestBody UserRegisterRequestDto dto, HttpServletRequest request) throws EmailServiceErrorException {

        User userEntity = new User();
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(PasswordEncoder.encode(dto.getPassword()));
        userEntity.setEmail(dto.getEmail());

        var otp = OtpUtils.generateOtp();
        otpService.associateOtpWithEmail(dto.getEmail(), otp);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(dto.getEmail());
        emailDetails.setSubject("HRM Account OTP");
        emailDetails.setMsgBody(otp+"");

        emailService.sendSimpleMail(emailDetails);
        otpService.associateOtpWithEmail(dto.getEmail(), otp);

        return userRepository.save(userEntity);






    }
}

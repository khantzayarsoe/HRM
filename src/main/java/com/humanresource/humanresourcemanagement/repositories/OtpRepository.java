package com.humanresource.humanresourcemanagement.repositories;


import com.humanresource.humanresourcemanagement.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//not use
@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmailAndCode(String email, int otpCode);
    void deleteAllByEmail(String email);
}
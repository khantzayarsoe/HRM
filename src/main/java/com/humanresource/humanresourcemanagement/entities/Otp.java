package com.humanresource.humanresourcemanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "otp_id")
    private Long id;
    private String email;
    private int code;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
}
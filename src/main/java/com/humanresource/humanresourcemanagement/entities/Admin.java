// entities/Admin.java
package com.humanresource.humanresourcemanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean isActive = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "admin_permissions",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions;


    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    // Hash password before saving
    public void hashPassword() {
        if (this.password != null && !this.password.startsWith("$2a$")) {
            this.password = BCrypt.hashpw(this.password, BCrypt.gensalt());
        }
    }

    // Verify password
    public boolean verifyPassword(String plainPassword) {
        if (plainPassword == null || this.password == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, this.password);
        } catch (Exception e) {
            return false;
        }
    }
}
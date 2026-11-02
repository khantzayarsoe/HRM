// repositories/AdminRepository.java
package com.humanresource.humanresourcemanagement.repositories;

import com.humanresource.humanresourcemanagement.entities.Admin;
import com.humanresource.humanresourcemanagement.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
    List<Admin> findByRole(Role role);
    boolean existsByUsername(String username);
}
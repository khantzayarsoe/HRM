// repositories/PermissionRepository.java
package com.humanresource.humanresourcemanagement.repositories;

import com.humanresource.humanresourcemanagement.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByIdIn(List<Long> ids);
}
// repositories/DepartmentFeesRepository.java
package com.humanresource.humanresourcemanagement.repositories;

import com.humanresource.humanresourcemanagement.entities.DepartmentFees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DepartmentFeesRepository extends JpaRepository<DepartmentFees, Long> {
    Optional<DepartmentFees> findByDeptId(String deptId);
    Optional<DepartmentFees> findByDeptName(String deptName);
}
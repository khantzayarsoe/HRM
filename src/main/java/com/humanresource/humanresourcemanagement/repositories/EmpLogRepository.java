package com.humanresource.humanresourcemanagement.repositories;


import com.humanresource.humanresourcemanagement.entities.EmpIdLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpLogRepository extends JpaRepository<EmpIdLog, String> {
    Optional<EmpIdLog> findByEmpID(String id);
}

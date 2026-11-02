package com.humanresource.humanresourcemanagement.repositories;


import com.humanresource.humanresourcemanagement.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findEmployeeByEmpID(String id);
}

package com.humanresource.humanresourcemanagement.repositories;


import com.humanresource.humanresourcemanagement.entities.ResignEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResignedEmployeeRepository extends JpaRepository<ResignEmployee, Long> {
}

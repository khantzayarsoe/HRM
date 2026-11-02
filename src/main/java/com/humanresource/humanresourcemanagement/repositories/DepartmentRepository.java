package com.humanresource.humanresourcemanagement.repositories;


import com.humanresource.humanresourcemanagement.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department,Long> {

}

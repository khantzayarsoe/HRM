package com.humanresource.humanresourcemanagement.services;

import com.humanresource.humanresourcemanagement.entities.Department;
import com.humanresource.humanresourcemanagement.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id){
        return departmentRepository.findById(id).orElseThrow(()-> new RuntimeException("Department Not Found"+id));
    }

    public  Department createDepartment(Department department){
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department departmentDetails){
        Department department=getDepartmentById(id);
        department.setDeptName(departmentDetails.getDeptName());
        department.setDesc(departmentDetails.getDesc());
        return departmentRepository.save(department);
    }

    public  void deleteDepartment(Long id){
         departmentRepository.deleteById(id);

    }

}

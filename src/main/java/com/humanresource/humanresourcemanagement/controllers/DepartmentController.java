package com.humanresource.humanresourcemanagement.controllers;


import com.humanresource.humanresourcemanagement.entities.Department;
import com.humanresource.humanresourcemanagement.entities.Employee;
import com.humanresource.humanresourcemanagement.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor

public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department> > getAllDepartment(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id){
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department){
        return ResponseEntity.ok(departmentService.createDepartment(department));
    }

    @PutMapping("/{id}")

    public  ResponseEntity<Department> updateDepartment( @PathVariable Long id, @RequestBody Department departmentDetails){

        return  ResponseEntity.ok(departmentService.updateDepartment(id,departmentDetails));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity< Void > deleteDepartment(@PathVariable Long id){
      departmentService.deleteDepartment(id);
      return ResponseEntity.noContent().build();

    }





}

package com.humanresource.humanresourcemanagement.controllers;

import com.humanresource.humanresourcemanagement.dto.EmployeeRequestDTO;
import com.humanresource.humanresourcemanagement.entities.Employee;
import com.humanresource.humanresourcemanagement.entities.ResignEmployee;
import com.humanresource.humanresourcemanagement.exceptions.IdNotAvaiableException;
import com.humanresource.humanresourcemanagement.services.EmployeeService;


import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
//@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.badRequest().body("File Storage Service failed");
    }

    @ExceptionHandler(IdNotAvaiableException.class)
    public ResponseEntity<String> handleIdNotAvailable(IdNotAvaiableException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
//        List<String> permission = List.of("read");
//        String scope = "employees";
//
//        extractScopeAndPermisssion(jwtOtkn);


//        List<String> allowed_users = List.of("admin");
//        String role = extractRole(jwtToken)
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> createEmployee(
            @RequestPart(name = "employee") EmployeeRequestDTO employeeRequestDTO,
            @RequestPart(name = "photo")MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(employeeService.createEmployee(employeeRequestDTO, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable String id,
            @RequestPart(name = "employee") EmployeeRequestDTO employeeDetails) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDetails));
    }


    @PutMapping("/{id}/photo")
    public ResponseEntity<Employee> updateEmployeePhoto(
            @PathVariable String id,
            @RequestPart(name = "photo") MultipartFile file) throws IOException {
        return ResponseEntity.ok(employeeService.updatePhoto(id, file));
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) throws IOException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/resign")
    public ResponseEntity<String> resignEmployee(@PathVariable String id) throws IOException{
        employeeService.resignEmployee(id);
        return ResponseEntity.ok("Employee resigned successfully");
    }

    @GetMapping("/resign")
    public ResponseEntity<List<ResignEmployee>> getAllEmployeesResign() {
//        List<String> permission = List.of("read");
//        String scope = "employees";
//
//        extractScopeAndPermisssion(jwtOtkn);


//        List<String> allowed_users = List.of("admin");
//        String role = extractRole(jwtToken)
        return ResponseEntity.ok(employeeService.getAllEmployeesResign());
    }

}
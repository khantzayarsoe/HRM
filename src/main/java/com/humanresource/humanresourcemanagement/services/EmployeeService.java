package com.humanresource.humanresourcemanagement.services;



import com.humanresource.humanresourcemanagement.config.FileStorageService;
import com.humanresource.humanresourcemanagement.dto.EmployeeRequestDTO;
import com.humanresource.humanresourcemanagement.entities.Employee;
import com.humanresource.humanresourcemanagement.entities.ResignEmployee;
import com.humanresource.humanresourcemanagement.exceptions.IdNotAvaiableException;
import com.humanresource.humanresourcemanagement.repositories.EmpLogRepository;
import com.humanresource.humanresourcemanagement.repositories.EmployeeRepository;
import com.humanresource.humanresourcemanagement.repositories.ResignedEmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final FileStorageService fileStorageService;
    private final ResignedEmployeeRepository resignedEmployeeRepository;
    private final EmpLogRepository empLogRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public List<ResignEmployee> getAllEmployeesResign() {
        return resignedEmployeeRepository.findAll();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findEmployeeByEmpID(id)
                .orElseThrow(() -> new RuntimeException("Employee not found " + id));
    }

    public boolean isIdAvailable(String id){
        var result = empLogRepository.findByEmpID(id).orElseGet(()->null);
        if(Objects.isNull(result)){
            return true;
        }
        return false;
    }


    @Transactional
    public Employee createEmployee(EmployeeRequestDTO requestDTO, MultipartFile multipartFile) throws IOException {


        if(!isIdAvailable(requestDTO.getEmpId())){
            throw new IdNotAvaiableException("Id is not available");
        }



        var employee = new Employee();

        employee.setEmpID(requestDTO.getEmpId());
        employee.setEmpName(requestDTO.getEmpName());
        employee.setDob(requestDTO.getDob());
        employee.setAddress(requestDTO.getAddress());
        employee.setNrc(requestDTO.getNrc());
        employee.setGender(requestDTO.getGender());
        employee.setDeptId(requestDTO.getDeptId());
        employee.setJoinDate(requestDTO.getJoinDate());
        employee.setFatherName(requestDTO.getFatherName());
        employee.setPhoneNo(requestDTO.getPhoneNo());
        employee.setPositionId(requestDTO.getPositionId());
        employee.setAge(requestDTO.getAge());


        var savedEmployee =  employeeRepository.save(employee);
        var storeImagePath = fileStorageService.storeFile(multipartFile, "employees");
        savedEmployee.setPhoto(storeImagePath);

        var employeeSavedWithPhoto = employeeRepository.save(savedEmployee);

        return employeeSavedWithPhoto;

    }

    public Employee updatePhoto(String id, MultipartFile file) throws IOException {
        Employee employee = getEmployeeById(id);
        var storedPath = employee.getPhoto();

        fileStorageService.deleteFile(storedPath);

        var newStoredPath = fileStorageService.storeFile(file, "employees");
        employee.setPhoto(newStoredPath);

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(String id, EmployeeRequestDTO employeeDetails) {
        Employee employee = getEmployeeById(id);

        employee.setEmpName(employeeDetails.getEmpName());
        employee.setDeptId(employeeDetails.getDeptId());
        employee.setPositionId(employeeDetails.getPositionId());
        employee.setNrc(employeeDetails.getNrc());
        employee.setAge(employeeDetails.getAge());
        employee.setJoinDate(employeeDetails.getJoinDate());
        employee.setPhoneNo(employeeDetails.getPhoneNo());
        employee.setAddress(employeeDetails.getAddress());
        employee.setFatherName(employeeDetails.getFatherName());
        employee.setDob(employeeDetails.getDob());
        employee.setGender(employeeDetails.getGender());

        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(String id) throws IOException {
        var employee = getEmployeeById(id);
        var storedPath = employee.getPhoto();

        fileStorageService.deleteFile(storedPath);

        employeeRepository.delete(employee);
    }

    @Transactional
    public void resignEmployee(String id) throws IOException {

        Employee employee = employeeRepository.findEmployeeByEmpID(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        ResignEmployee resigned = new ResignEmployee();
        resigned.setEmpID(employee.getEmpID());
        resigned.setEmpName(employee.getEmpName());
        resigned.setDeptId(employee.getDeptId());
        resigned.setPositionId(employee.getPositionId());
        resigned.setNrc(employee.getNrc());
        resigned.setAge(employee.getAge());
        resigned.setJoinDate(employee.getJoinDate());
        resigned.setPhoneNo(employee.getPhoneNo());
        resigned.setAddress(employee.getAddress());
        resigned.setFatherName(employee.getFatherName());
        resigned.setDob(employee.getDob());
        resigned.setGender(employee.getGender());
        resigned.setResignedDate(LocalDate.now());
        resigned.setPhoto(employee.getPhoto());

        resignedEmployeeRepository.save(resigned);

        // Delete from employee table
        employeeRepository.delete(employee);

    }

}


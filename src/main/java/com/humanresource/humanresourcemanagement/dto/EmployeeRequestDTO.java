package com.humanresource.humanresourcemanagement.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Data
public class EmployeeRequestDTO {
    @Nullable
    private String empId;
    private String empName;
    private String deptId;
    private String positionId;
    private String nrc;
    private Integer age;
    private LocalDate joinDate;
    private String phoneNo;
    private String address;
    private String fatherName;
    private LocalDate dob;
    private String gender;
}
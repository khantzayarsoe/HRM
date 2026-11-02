package com.humanresource.humanresourcemanagement.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeResponseDTO {
    private Long empID;
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
    private boolean hasPhoto;
    private String photoUrl;
}
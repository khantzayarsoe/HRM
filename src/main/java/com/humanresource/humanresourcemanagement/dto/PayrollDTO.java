//// dto/PayrollDTO.java
//package com.humanresource.humanresourcemanagement.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.math.BigDecimal;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class PayrollDTO {
//    private Long id;
//    private String empId;
//    private String empName;
//    private String deptId;
//    private String deptName;
//    private String positionName;
//    private String payrollMonth;
//    private BigDecimal basicSalary;
//    private BigDecimal departmentFees;
//    private BigDecimal positionFees;  // Add this field
//    private BigDecimal bonusAmount;
//    private BigDecimal otherAllowance;
//    private BigDecimal regularAttendFees;
//    private BigDecimal deductions;
//    private BigDecimal totalEarnings;
//    private BigDecimal netSalary;
//    private String status;
//}


// dto/PayrollDTO.java
package com.humanresource.humanresourcemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollDTO {
    private Long id;
    private String empId;
    private String empName;
    private String deptId;
    private String deptName;
    private String positionName;
    private String payrollMonth;
    private BigDecimal basicSalary;  // Add this field
    private BigDecimal departmentFees;
    private BigDecimal positionFees;
    private BigDecimal bonusAmount;
    private BigDecimal otherAllowance;
    private BigDecimal regularAttendFees;
    private BigDecimal deductions;
    private BigDecimal totalEarnings;
    private BigDecimal netSalary;
    private String status;
}
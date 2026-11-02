//// dto/PayrollFormDTO.java
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
//public class PayrollFormDTO {
//    private String empId;
//    private String empName;
//    private String deptId;
//    private String deptName;
//    private Long positionId;
//    private String positionName;
//    private BigDecimal basicSalary;
//    private BigDecimal departmentFees;
//    private BigDecimal positionFees;  // Add this field
//    private BigDecimal bonusAmount;
//    private BigDecimal otherAllowance;
//    private BigDecimal regularAttendFees;
//    private BigDecimal deductions;
//    private String payrollMonth;
//}

// dto/PayrollFormDTO.java
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
public class PayrollFormDTO {
    private String empId;
    private String empName;
    private String deptId;
    private String deptName;
    private Long positionId;
    private String positionName;
    private BigDecimal basicSalary;
    private BigDecimal departmentFees;
    private BigDecimal positionFees;
    private BigDecimal bonusAmount;
    private BigDecimal otherAllowance;
    private BigDecimal regularAttendFees;
    private BigDecimal deductions;
    private String payrollMonth;
}
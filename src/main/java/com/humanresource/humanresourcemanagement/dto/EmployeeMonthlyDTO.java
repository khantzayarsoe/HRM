// dto/EmployeeMonthlyDTO.java
package com.humanresource.humanresourcemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeMonthlyDTO {
    private String empId;
    private String empName;
    private String deptId;
    private int presentDays;
    private int absentDays;
    private int leaveDays;
    private int halfDays;
    private int holidayDays;
    private int totalDays;
    private int workingDays;
    private double attendancePercentage;
}
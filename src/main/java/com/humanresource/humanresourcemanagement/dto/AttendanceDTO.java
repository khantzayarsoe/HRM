// dto/AttendanceDTO.java
package com.humanresource.humanresourcemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
    private String empId;
    private String empName;
    private String deptId;
    private LocalDate attendanceDate;
    private String status;
    private String remarks;
}
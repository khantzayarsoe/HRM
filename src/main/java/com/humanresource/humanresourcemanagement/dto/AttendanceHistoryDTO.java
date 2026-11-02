// dto/AttendanceHistoryDTO.java
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
public class AttendanceHistoryDTO {
    private Long id;
    private String empId;
    private String empName;
    private LocalDate attendanceDate;
    private String status;
    private String remarks;
    private String markedBy;
    private String markedAt;
}
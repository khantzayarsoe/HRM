// dto/DepartmentFeesDTO.java
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
public class DepartmentFeesDTO {
    private String deptId;
    private String deptName;
    private BigDecimal feesAmount;
    private String description;
}
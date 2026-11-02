//// dto/PositionFeesDTO.java
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
//public class PositionFeesDTO {
//    private Long positionId;
//    private String positionName;
//    private BigDecimal feesAmount;
//    private String description;
//}

// dto/PositionFeesDTO.java
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
public class PositionFeesDTO {
    private Long positionId;
    private String positionName;
    private BigDecimal basicSalary;
    private BigDecimal feesAmount;
    private String description;
}
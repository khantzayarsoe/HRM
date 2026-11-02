//// entities/PositionFees.java
//package com.humanresource.humanresourcemanagement.entities;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "position_fees")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class PositionFees {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "position_id", nullable = false, unique = true)
//    private Long positionId;
//
//    @Column(name = "position_name", nullable = false)
//    private String positionName;
//
//    @Column(name = "fees_amount", nullable = false)
//    private BigDecimal feesAmount;
//
//    @Column(name = "description")
//    private String description;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//}


// entities/PositionFees.java
package com.humanresource.humanresourcemanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "position_fees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionFees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_id", nullable = false, unique = true)
    private Long positionId;

    @Column(name = "position_name", nullable = false)
    private String positionName;

    @Column(name = "basic_salary", nullable = false)  // Add this field
    private BigDecimal basicSalary;

    @Column(name = "fees_amount", nullable = false)
    private BigDecimal feesAmount;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
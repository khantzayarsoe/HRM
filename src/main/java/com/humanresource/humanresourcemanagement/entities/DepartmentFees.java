// entities/DepartmentFees.java
package com.humanresource.humanresourcemanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "department_fees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentFees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dept_id", nullable = false, unique = true)
    private String deptId;

    @Column(name = "dept_name", nullable = false)
    private String deptName;

    @Column(name = "fees_amount", nullable = false)
    private BigDecimal feesAmount;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
// entities/Payroll.java
package com.humanresource.humanresourcemanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payroll")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_id", nullable = false)
    private String empId;

    @Column(name = "emp_name", nullable = false)
    private String empName;

    @Column(name = "dept_id")
    private String deptId;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "payroll_month", nullable = false)
    private String payrollMonth; // Format: YYYY-MM

    // Fixed values from position and department
    @Column(name = "basic_salary", nullable = false)
    private BigDecimal basicSalary;

    @Column(name = "department_fees", nullable = false)
    private BigDecimal departmentFees;

    // Editable fields
    @Column(name = "bonus_amount")
    private BigDecimal bonusAmount;

    @Column(name = "other_allowance")
    private BigDecimal otherAllowance;

    @Column(name = "regular_attend_fees")
    private BigDecimal regularAttendFees;

    @Column(name = "deductions")
    private BigDecimal deductions;

    // Calculated fields
    @Column(name = "total_earnings", nullable = false)
    private BigDecimal totalEarnings;

    @Column(name = "net_salary", nullable = false)
    private BigDecimal netSalary;

    // Status
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PayrollStatus status;

    @Column(name = "remarks")
    private String remarks;
    // entities/Payroll.java (add position_fees field)
    @Column(name = "position_fees")
    private BigDecimal positionFees;





    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
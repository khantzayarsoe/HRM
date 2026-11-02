// entities/MonthlyAttendanceSummary.java
package com.humanresource.humanresourcemanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_attendance_summary",
        uniqueConstraints = @UniqueConstraint(columnNames = {"emp_id", "attendance_month"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyAttendanceSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_id", nullable = false)
    private String empId;

    @Column(name = "emp_name", nullable = false)
    private String empName;

    @Column(name = "dept_id")
    private String deptId;

    @Column(name = "attendance_month", nullable = false)  // Changed from year_month to attendance_month
    private String yearMonth; // Keep the field name as yearMonth but map to attendance_month

    @Column(name = "total_days", nullable = false)
    private int totalDays;

    @Column(name = "present_days", nullable = false)
    private int presentDays;

    @Column(name = "absent_days", nullable = false)
    private int absentDays;

    @Column(name = "leave_days", nullable = false)
    private int leaveDays;

    @Column(name = "half_days", nullable = false)
    private int halfDays;

    @Column(name = "holiday_days", nullable = false)
    private int holidayDays;

    @Column(name = "working_days", nullable = false)
    private int workingDays;

    @Column(name = "attendance_percentage", nullable = false)
    private double attendancePercentage;

    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;
}
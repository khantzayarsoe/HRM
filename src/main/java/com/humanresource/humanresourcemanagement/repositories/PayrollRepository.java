// repositories/PayrollRepository.java
package com.humanresource.humanresourcemanagement.repositories;

import com.humanresource.humanresourcemanagement.entities.Payroll;
import com.humanresource.humanresourcemanagement.entities.PayrollStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByPayrollMonthOrderByEmpNameAsc(String payrollMonth);

    Optional<Payroll> findByEmpIdAndPayrollMonth(String empId, String payrollMonth);

    List<Payroll> findByEmpIdOrderByPayrollMonthDesc(String empId);

    List<Payroll> findByStatusAndPayrollMonth(PayrollStatus status, String payrollMonth);

    @Query("SELECT COALESCE(SUM(p.netSalary), 0) FROM Payroll p WHERE p.payrollMonth = :month")
    BigDecimal getTotalNetSalaryForMonth(@Param("month") String month);

    @Query("SELECT COUNT(p) FROM Payroll p WHERE p.payrollMonth = :month AND p.status = :status")
    Long countByStatusAndMonth(@Param("status") PayrollStatus status, @Param("month") String month);

    boolean existsByEmpIdAndPayrollMonth(String empId, String payrollMonth);

    void deleteByEmpIdAndPayrollMonth(String empId, String payrollMonth);
}
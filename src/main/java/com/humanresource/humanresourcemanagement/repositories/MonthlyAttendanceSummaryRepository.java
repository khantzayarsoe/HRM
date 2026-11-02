// repositories/MonthlyAttendanceSummaryRepository.java
package com.humanresource.humanresourcemanagement.repositories;

import com.humanresource.humanresourcemanagement.entities.MonthlyAttendanceSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyAttendanceSummaryRepository extends JpaRepository<MonthlyAttendanceSummary, Long> {

    Optional<MonthlyAttendanceSummary> findByEmpIdAndYearMonth(String empId, String yearMonth);

    List<MonthlyAttendanceSummary> findByYearMonth(String yearMonth);

    @Query("SELECT s FROM MonthlyAttendanceSummary s WHERE s.empId = :empId ORDER BY s.yearMonth DESC")
    List<MonthlyAttendanceSummary> findByEmpIdOrderByYearMonthDesc(@Param("empId") String empId);

    @Query("SELECT s FROM MonthlyAttendanceSummary s WHERE s.yearMonth = :yearMonth ORDER BY s.attendancePercentage DESC")
    List<MonthlyAttendanceSummary> findByYearMonthOrderByAttendancePercentage(@Param("yearMonth") String yearMonth);
}
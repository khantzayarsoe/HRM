// repositories/AttendanceRepository.java
package com.humanresource.humanresourcemanagement.repositories;

import com.humanresource.humanresourcemanagement.entities.Attendance;
import com.humanresource.humanresourcemanagement.entities.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {



    Optional<Attendance> findByEmpIdAndAttendanceDate(String empId, LocalDate date);

    List<Attendance> findByAttendanceDate(LocalDate date);

    List<Attendance> findByEmpIdAndAttendanceDateBetween(String empId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT a FROM Attendance a WHERE YEAR(a.attendanceDate) = :year AND MONTH(a.attendanceDate) = :month")
    List<Attendance> findByMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT a FROM Attendance a WHERE a.empId = :empId AND YEAR(a.attendanceDate) = :year AND MONTH(a.attendanceDate) = :month")
    List<Attendance> findByEmpIdAndMonth(@Param("empId") String empId,
                                         @Param("year") int year,
                                         @Param("month") int month);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.empId = :empId AND a.status = :status AND YEAR(a.attendanceDate) = :year AND MONTH(a.attendanceDate) = :month")
    int countByEmpIdAndStatusAndMonth(@Param("empId") String empId,
                                      @Param("status") AttendanceStatus status,
                                      @Param("year") int year,
                                      @Param("month") int month);

    @Query("SELECT DISTINCT a.empId FROM Attendance a WHERE YEAR(a.attendanceDate) = :year AND MONTH(a.attendanceDate) = :month")
    List<String> findDistinctEmpIdsByMonth(@Param("year") int year, @Param("month") int month);
}
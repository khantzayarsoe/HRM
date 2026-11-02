// services/AttendanceService.java
package com.humanresource.humanresourcemanagement.services;

import com.humanresource.humanresourcemanagement.dto.AttendanceDTO;
import com.humanresource.humanresourcemanagement.dto.AttendanceHistoryDTO;
import com.humanresource.humanresourcemanagement.dto.EmployeeMonthlyDTO;
import com.humanresource.humanresourcemanagement.dto.MonthlyAttendanceDTO;
import com.humanresource.humanresourcemanagement.entities.Attendance;
import com.humanresource.humanresourcemanagement.entities.AttendanceStatus;
import com.humanresource.humanresourcemanagement.entities.MonthlyAttendanceSummary;
import com.humanresource.humanresourcemanagement.repositories.AttendanceRepository;
import com.humanresource.humanresourcemanagement.repositories.MonthlyAttendanceSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MonthlyAttendanceSummaryRepository summaryRepository;

    @Transactional
    public Attendance markAttendance(AttendanceDTO dto) {
        AttendanceStatus status = AttendanceStatus.valueOf(dto.getStatus().toUpperCase());

        Attendance attendance = attendanceRepository
                .findByEmpIdAndAttendanceDate(dto.getEmpId(), dto.getAttendanceDate())
                .orElse(Attendance.builder()
                        .empId(dto.getEmpId())
                        .empName(dto.getEmpName())
                        .deptId(dto.getDeptId())
                        .attendanceDate(dto.getAttendanceDate())
                        .build());

        attendance.setStatus(status);
        attendance.setRemarks(dto.getRemarks());
        attendance.setMarkedBy("SYSTEM");
        attendance.setMarkedAt(LocalDateTime.now());
        attendance.setUpdatedAt(LocalDateTime.now());

        return attendanceRepository.save(attendance);
    }

    @Transactional
    public List<Attendance> markBulkAttendance(List<AttendanceDTO> dtos) {
        List<Attendance> attendances = dtos.stream().map(dto -> {
            AttendanceStatus status = AttendanceStatus.valueOf(dto.getStatus().toUpperCase());
            return Attendance.builder()
                    .empId(dto.getEmpId())
                    .empName(dto.getEmpName())
                    .deptId(dto.getDeptId())
                    .attendanceDate(dto.getAttendanceDate())
                    .status(status)
                    .remarks(dto.getRemarks())
                    .markedBy("SYSTEM")
                    .markedAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }).collect(Collectors.toList());

        return attendanceRepository.saveAll(attendances);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date);
    }

    public List<Attendance> getAttendanceByMonth(int year, int month) {
        return attendanceRepository.findByMonth(year, month);
    }

    public List<Attendance> getEmployeeAttendanceByMonth(String empId, int year, int month) {
        return attendanceRepository.findByEmpIdAndMonth(empId, year, month);
    }

    public List<AttendanceHistoryDTO> getAttendanceHistory(String empId, int year, int month) {
        List<Attendance> attendances = attendanceRepository.findByEmpIdAndMonth(empId, year, month);

        return attendances.stream()
                .map(a -> AttendanceHistoryDTO.builder()
                        .id(a.getId())
                        .empId(a.getEmpId())
                        .empName(a.getEmpName())
                        .attendanceDate(a.getAttendanceDate())
                        .status(a.getStatus().toString())
                        .remarks(a.getRemarks())
                        .markedBy(a.getMarkedBy())
                        .markedAt(a.getMarkedAt() != null ? a.getMarkedAt().toString() : null)
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public MonthlyAttendanceSummary calculateMonthlySummary(String empId, int year, int month) {
        String yearMonth = String.format("%d-%02d", year, month);
        YearMonth ym = YearMonth.of(year, month);
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atEndOfMonth();

        List<Attendance> attendances = attendanceRepository
                .findByEmpIdAndAttendanceDateBetween(empId, startDate, endDate);

        if (attendances.isEmpty()) {
            return null;
        }

        Map<AttendanceStatus, Long> counts = attendances.stream()
                .collect(Collectors.groupingBy(Attendance::getStatus, Collectors.counting()));

        int totalDays = endDate.getDayOfMonth();
        int presentDays = counts.getOrDefault(AttendanceStatus.PRESENT, 0L).intValue();
        int absentDays = counts.getOrDefault(AttendanceStatus.ABSENT, 0L).intValue();
        int leaveDays = counts.getOrDefault(AttendanceStatus.LEAVE, 0L).intValue();
        int halfDays = counts.getOrDefault(AttendanceStatus.HALF_DAY, 0L).intValue();
        int holidayDays = counts.getOrDefault(AttendanceStatus.HOLIDAY, 0L).intValue();

        int workingDays = totalDays - holidayDays;
        double attendancePercentage = workingDays > 0
                ? ((presentDays + (halfDays * 0.5)) / workingDays) * 100
                : 0;

        Attendance firstAttendance = attendances.get(0);

        MonthlyAttendanceSummary summary = summaryRepository
                .findByEmpIdAndYearMonth(empId, yearMonth)
                .orElse(MonthlyAttendanceSummary.builder()
                        .empId(empId)
                        .empName(firstAttendance.getEmpName())
                        .deptId(firstAttendance.getDeptId())
                        .yearMonth(yearMonth)
                        .build());

        summary.setPresentDays(presentDays);
        summary.setAbsentDays(absentDays);
        summary.setLeaveDays(leaveDays);
        summary.setHalfDays(halfDays);
        summary.setHolidayDays(holidayDays);
        summary.setTotalDays(totalDays);
        summary.setWorkingDays(workingDays);
        summary.setAttendancePercentage(Math.round(attendancePercentage * 100.0) / 100.0);
        summary.setCalculatedAt(LocalDateTime.now());

        return summaryRepository.save(summary);
    }

    @Transactional
    public List<MonthlyAttendanceSummary> calculateAllMonthlySummaries(int year, int month) {
        String yearMonth = String.format("%d-%02d", year, month);
        YearMonth ym = YearMonth.of(year, month);
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atEndOfMonth();

        List<Attendance> allAttendances = attendanceRepository.findByMonth(year, month);

        Map<String, List<Attendance>> attendancesByEmployee = allAttendances.stream()
                .collect(Collectors.groupingBy(Attendance::getEmpId));

        List<MonthlyAttendanceSummary> summaries = attendancesByEmployee.entrySet().stream()
                .map(entry -> {
                    String empId = entry.getKey();
                    List<Attendance> empAttendances = entry.getValue();

                    Map<AttendanceStatus, Long> counts = empAttendances.stream()
                            .collect(Collectors.groupingBy(Attendance::getStatus, Collectors.counting()));

                    int totalDays = endDate.getDayOfMonth();
                    int presentDays = counts.getOrDefault(AttendanceStatus.PRESENT, 0L).intValue();
                    int absentDays = counts.getOrDefault(AttendanceStatus.ABSENT, 0L).intValue();
                    int leaveDays = counts.getOrDefault(AttendanceStatus.LEAVE, 0L).intValue();
                    int halfDays = counts.getOrDefault(AttendanceStatus.HALF_DAY, 0L).intValue();
                    int holidayDays = counts.getOrDefault(AttendanceStatus.HOLIDAY, 0L).intValue();

                    int workingDays = totalDays - holidayDays;
                    double attendancePercentage = workingDays > 0
                            ? ((presentDays + (halfDays * 0.5)) / workingDays) * 100
                            : 0;

                    Attendance firstAttendance = empAttendances.get(0);

                    MonthlyAttendanceSummary summary = summaryRepository
                            .findByEmpIdAndYearMonth(empId, yearMonth)
                            .orElse(MonthlyAttendanceSummary.builder()
                                    .empId(empId)
                                    .empName(firstAttendance.getEmpName())
                                    .deptId(firstAttendance.getDeptId())
                                    .yearMonth(yearMonth)
                                    .build());

                    summary.setPresentDays(presentDays);
                    summary.setAbsentDays(absentDays);
                    summary.setLeaveDays(leaveDays);
                    summary.setHalfDays(halfDays);
                    summary.setHolidayDays(holidayDays);
                    summary.setTotalDays(totalDays);
                    summary.setWorkingDays(workingDays);
                    summary.setAttendancePercentage(Math.round(attendancePercentage * 100.0) / 100.0);
                    summary.setCalculatedAt(LocalDateTime.now());

                    return summary;
                })
                .collect(Collectors.toList());

        return summaryRepository.saveAll(summaries);
    }

    @Transactional
    public MonthlyAttendanceDTO submitMonthlyAttendance(MonthlyAttendanceDTO dto) {
        String yearMonth = dto.getYearMonth();

        for (EmployeeMonthlyDTO emp : dto.getEmployees()) {
            MonthlyAttendanceSummary summary = summaryRepository
                    .findByEmpIdAndYearMonth(emp.getEmpId(), yearMonth)
                    .orElse(MonthlyAttendanceSummary.builder()
                            .empId(emp.getEmpId())
                            .empName(emp.getEmpName())
                            .deptId(emp.getDeptId())
                            .yearMonth(yearMonth)
                            .build());

            summary.setPresentDays(emp.getPresentDays());
            summary.setAbsentDays(emp.getAbsentDays());
            summary.setLeaveDays(emp.getLeaveDays());
            summary.setHalfDays(emp.getHalfDays());
            summary.setHolidayDays(emp.getHolidayDays());
            summary.setTotalDays(emp.getTotalDays());
            summary.setWorkingDays(emp.getWorkingDays());
            summary.setAttendancePercentage(emp.getAttendancePercentage());
            summary.setCalculatedAt(LocalDateTime.now());

            summaryRepository.save(summary);
        }

        return dto;
    }

    public List<MonthlyAttendanceSummary> getMonthlySummaries(int year, int month) {
        String yearMonth = String.format("%d-%02d", year, month);
        return summaryRepository.findByYearMonth(yearMonth);
    }

    public List<MonthlyAttendanceSummary> getEmployeeHistory(String empId) {
        return summaryRepository.findByEmpIdOrderByYearMonthDesc(empId);
    }

    public List<MonthlyAttendanceSummary> getAllHistory() {
        return summaryRepository.findAll();
    }
}
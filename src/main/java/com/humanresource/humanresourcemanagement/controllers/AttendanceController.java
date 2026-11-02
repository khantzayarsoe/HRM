// controllers/AttendanceController.java
package com.humanresource.humanresourcemanagement.controllers;

import com.humanresource.humanresourcemanagement.dto.AttendanceDTO;
import com.humanresource.humanresourcemanagement.dto.AttendanceHistoryDTO;
import com.humanresource.humanresourcemanagement.dto.MonthlyAttendanceDTO;
import com.humanresource.humanresourcemanagement.entities.Attendance;
import com.humanresource.humanresourcemanagement.entities.MonthlyAttendanceSummary;
import com.humanresource.humanresourcemanagement.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

//    @PostMapping("/mark")
//    public ResponseEntity<Attendance> markAttendance(@RequestBody AttendanceDTO dto) {
//        return ResponseEntity.ok(attendanceService.markAttendance(dto));
//    }

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody AttendanceDTO dto) {
        try {
            System.out.println("Received attendance: " + dto); // Add logging
            Attendance attendance = attendanceService.markAttendance(dto);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            e.printStackTrace(); // Print full error
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Attendance>> markBulkAttendance(@RequestBody List<AttendanceDTO> dtos) {
        return ResponseEntity.ok(attendanceService.markBulkAttendance(dtos));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Attendance>> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(date));
    }

    @GetMapping("/month/{year}/{month}")
    public ResponseEntity<List<Attendance>> getAttendanceByMonth(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(attendanceService.getAttendanceByMonth(year, month));
    }

    @GetMapping("/employee/{empId}/month/{year}/{month}")
    public ResponseEntity<List<Attendance>> getEmployeeAttendanceByMonth(
            @PathVariable String empId,
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(attendanceService.getEmployeeAttendanceByMonth(empId, year, month));
    }

    @GetMapping("/employee/{empId}/history/{year}/{month}")
    public ResponseEntity<List<AttendanceHistoryDTO>> getAttendanceHistory(
            @PathVariable String empId,
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(attendanceService.getAttendanceHistory(empId, year, month));
    }

    @PostMapping("/calculate/{empId}/{year}/{month}")
    public ResponseEntity<MonthlyAttendanceSummary> calculateMonthlySummary(
            @PathVariable String empId,
            @PathVariable int year,
            @PathVariable int month) {
        MonthlyAttendanceSummary summary = attendanceService.calculateMonthlySummary(empId, year, month);
        return summary != null ? ResponseEntity.ok(summary) : ResponseEntity.notFound().build();
    } // test

    @PostMapping("/calculate-all/{year}/{month}")
    public ResponseEntity<List<MonthlyAttendanceSummary>> calculateAllMonthlySummaries(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(attendanceService.calculateAllMonthlySummaries(year, month));
    }

    @PostMapping("/submit-monthly")
    public ResponseEntity<MonthlyAttendanceDTO> submitMonthlyAttendance(@RequestBody MonthlyAttendanceDTO dto) {
        return ResponseEntity.ok(attendanceService.submitMonthlyAttendance(dto));
    }

    @GetMapping("/summaries/{year}/{month}")
    public ResponseEntity<List<MonthlyAttendanceSummary>> getMonthlySummaries(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(attendanceService.getMonthlySummaries(year, month));
    }

    @GetMapping("/employee/{empId}/history")
    public ResponseEntity<List<MonthlyAttendanceSummary>> getEmployeeHistory(@PathVariable String empId) {
        return ResponseEntity.ok(attendanceService.getEmployeeHistory(empId));
    }

    @GetMapping("/history/all")
    public ResponseEntity<List<MonthlyAttendanceSummary>> getAllHistory() {
        return ResponseEntity.ok(attendanceService.getAllHistory());
    }
}
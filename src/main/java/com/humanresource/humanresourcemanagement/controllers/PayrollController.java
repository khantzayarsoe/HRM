// controllers/PayrollController.java
package com.humanresource.humanresourcemanagement.controllers;

import com.humanresource.humanresourcemanagement.dto.DepartmentFeesDTO;
import com.humanresource.humanresourcemanagement.dto.PayrollDTO;
import com.humanresource.humanresourcemanagement.dto.PayrollFormDTO;
import com.humanresource.humanresourcemanagement.dto.PositionFeesDTO;
import com.humanresource.humanresourcemanagement.entities.PayrollStatus;
import com.humanresource.humanresourcemanagement.services.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    // External data endpoints
    @GetMapping("/external/departments")
    public ResponseEntity<List<Map<String, Object>>> getAllDepartments() {
        return ResponseEntity.ok(payrollService.getAllDepartments());
    }

    @GetMapping("/external/positions")
    public ResponseEntity<List<Map<String, Object>>> getAllPositions() {
        return ResponseEntity.ok(payrollService.getAllPositions());
    }

    @GetMapping("/external/employees")
    public ResponseEntity<List<Map<String, Object>>> getAllEmployees() {
        return ResponseEntity.ok(payrollService.getAllEmployees());
    }

    // Department Fees Management
    @PostMapping("/department-fees")
    public ResponseEntity<DepartmentFeesDTO> saveDepartmentFees(@RequestBody DepartmentFeesDTO dto) {
        return ResponseEntity.ok(payrollService.saveDepartmentFees(dto));
    }

    @GetMapping("/department-fees")
    public ResponseEntity<List<DepartmentFeesDTO>> getAllDepartmentFees() {
        return ResponseEntity.ok(payrollService.getAllDepartmentFees());
    }

    @GetMapping("/department-fees/{deptId}")
    public ResponseEntity<DepartmentFeesDTO> getDepartmentFees(@PathVariable String deptId) {
        DepartmentFeesDTO dto = payrollService.getDepartmentFees(deptId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // Position Fees Management
    @PostMapping("/position-fees")
    public ResponseEntity<PositionFeesDTO> savePositionFees(@RequestBody PositionFeesDTO dto) {
        return ResponseEntity.ok(payrollService.savePositionFees(dto));
    }

    @GetMapping("/position-fees")
    public ResponseEntity<List<PositionFeesDTO>> getAllPositionFees() {
        return ResponseEntity.ok(payrollService.getAllPositionFees());
    }

    @GetMapping("/position-fees/{positionId}")
    public ResponseEntity<PositionFeesDTO> getPositionFees(@PathVariable Long positionId) {
        PositionFeesDTO dto = payrollService.getPositionFees(positionId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // Payroll Form Data
    @GetMapping("/form/{empId}/{payrollMonth}")
    public ResponseEntity<PayrollFormDTO> getPayrollFormData(
            @PathVariable String empId,
            @PathVariable String payrollMonth) {
        return ResponseEntity.ok(payrollService.getPayrollFormData(empId, payrollMonth));
    }

    // Payroll CRUD
    @PostMapping("/save")
    public ResponseEntity<PayrollDTO> savePayroll(@RequestBody PayrollFormDTO formDTO) {
        return ResponseEntity.ok(payrollService.savePayroll(formDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollDTO> getPayrollById(@PathVariable Long id) {
        return ResponseEntity.ok(payrollService.getPayrollById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollDTO> updatePayroll(
            @PathVariable Long id,
            @RequestBody PayrollFormDTO formDTO) {
        return ResponseEntity.ok(payrollService.updatePayroll(id, formDTO));
    }

    @GetMapping("/month/{payrollMonth}")
    public ResponseEntity<List<PayrollDTO>> getPayrollsByMonth(@PathVariable String payrollMonth) {
        return ResponseEntity.ok(payrollService.getPayrollsByMonth(payrollMonth));
    }

    @GetMapping("/employee/{empId}/history")
    public ResponseEntity<List<PayrollDTO>> getEmployeePayrollHistory(@PathVariable String empId) {
        return ResponseEntity.ok(payrollService.getEmployeePayrollHistory(empId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PayrollDTO> updatePayrollStatus(
            @PathVariable Long id,
            @RequestParam PayrollStatus status) {
        return ResponseEntity.ok(payrollService.updatePayrollStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long id) {
        payrollService.deletePayroll(id);
        return ResponseEntity.ok().build();
    }
}
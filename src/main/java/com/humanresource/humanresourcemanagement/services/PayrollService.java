// services/PayrollService.java
package com.humanresource.humanresourcemanagement.services;

import com.humanresource.humanresourcemanagement.dto.DepartmentFeesDTO;
import com.humanresource.humanresourcemanagement.dto.PayrollDTO;
import com.humanresource.humanresourcemanagement.dto.PayrollFormDTO;
import com.humanresource.humanresourcemanagement.dto.PositionFeesDTO;
import com.humanresource.humanresourcemanagement.entities.*;
import com.humanresource.humanresourcemanagement.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final DepartmentFeesRepository departmentFeesRepository;
    private final PositionFeesRepository positionFeesRepository;
    private final RestTemplate restTemplate;

    // API endpoints
    private final String DEPT_API = "http://localhost:8080/api/departments";
    private final String POS_API = "http://localhost:8080/api/positions";
    private final String EMP_API = "http://localhost:8080/api/employees";

    // Get all departments from external API
    public List<Map<String, Object>> getAllDepartments() {
        try {
            Map<String, Object>[] departments = restTemplate.getForObject(DEPT_API, Map[].class);
            return departments != null ? Arrays.asList(departments) : List.of();
        } catch (Exception e) {
            log.error("Error fetching departments: {}", e.getMessage());
            return List.of();
        }
    }

    // Get all positions from external API
    public List<Map<String, Object>> getAllPositions() {
        try {
            Map<String, Object>[] positions = restTemplate.getForObject(POS_API, Map[].class);
            return positions != null ? Arrays.asList(positions) : List.of();
        } catch (Exception e) {
            log.error("Error fetching positions: {}", e.getMessage());
            return List.of();
        }
    }

    // Get all employees from external API
    public List<Map<String, Object>> getAllEmployees() {
        try {
            Map<String, Object>[] employees = restTemplate.getForObject(EMP_API, Map[].class);
            return employees != null ? Arrays.asList(employees) : List.of();
        } catch (Exception e) {
            log.error("Error fetching employees: {}", e.getMessage());
            return List.of();
        }
    }

    // Department Fees Management
    @Transactional
    public DepartmentFeesDTO saveDepartmentFees(DepartmentFeesDTO dto) {
        DepartmentFees fees = departmentFeesRepository.findByDeptId(dto.getDeptId())
                .orElse(new DepartmentFees());

        fees.setDeptId(dto.getDeptId());
        fees.setDeptName(dto.getDeptName());
        fees.setFeesAmount(dto.getFeesAmount());
        fees.setDescription(dto.getDescription());
        fees.setCreatedAt(fees.getCreatedAt() != null ? fees.getCreatedAt() : LocalDateTime.now());
        fees.setUpdatedAt(LocalDateTime.now());

        departmentFeesRepository.save(fees);
        return dto;
    }

    public List<DepartmentFeesDTO> getAllDepartmentFees() {
        return departmentFeesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DepartmentFeesDTO getDepartmentFees(String deptId) {
        return departmentFeesRepository.findByDeptId(deptId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Position Fees Management
//    @Transactional
//    public PositionFeesDTO savePositionFees(PositionFeesDTO dto) {
//        PositionFees fees = positionFeesRepository.findByPositionId(dto.getPositionId())
//                .orElse(new PositionFees());
//
//        fees.setPositionId(dto.getPositionId());
//        fees.setPositionName(dto.getPositionName());
//        fees.setFeesAmount(dto.getFeesAmount());
//        fees.setDescription(dto.getDescription());
//        fees.setCreatedAt(fees.getCreatedAt() != null ? fees.getCreatedAt() : LocalDateTime.now());
//        fees.setUpdatedAt(LocalDateTime.now());
//
//        positionFeesRepository.save(fees);
//        return dto;
//    }


    @Transactional
    public PositionFeesDTO savePositionFees(PositionFeesDTO dto) {
        PositionFees fees = positionFeesRepository.findByPositionId(dto.getPositionId())
                .orElse(new PositionFees());

        fees.setPositionId(dto.getPositionId());
        fees.setPositionName(dto.getPositionName());
        fees.setBasicSalary(dto.getBasicSalary());  // Add this line
        fees.setFeesAmount(dto.getFeesAmount());
        fees.setDescription(dto.getDescription());
        fees.setCreatedAt(fees.getCreatedAt() != null ? fees.getCreatedAt() : LocalDateTime.now());
        fees.setUpdatedAt(LocalDateTime.now());

        positionFeesRepository.save(fees);
        return dto;
    }

    public List<PositionFeesDTO> getAllPositionFees() {
        return positionFeesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PositionFeesDTO getPositionFees(Long positionId) {
        return positionFeesRepository.findByPositionId(positionId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Get payroll form data for an employee
//    public PayrollFormDTO getPayrollFormData(String empId, String payrollMonth) {
//        log.debug("Getting payroll form data for empId: {}, month: {}", empId, payrollMonth);
//
//        // Get employee details from API
//        Map<String, Object> employee = getEmployeeById(empId);
//        if (employee == null) {
//            throw new RuntimeException("Employee not found with ID: " + empId);
//        }
//
//        // Get department fees from database
//        String deptId = (String) employee.get("deptId");
//        if (deptId == null) {
//            deptId = (String) employee.get("dept_id");
//        }
//
//        DepartmentFeesDTO deptFees = deptId != null ? getDepartmentFees(deptId) : null;
//
//        // Get position ID
//        Object positionIdObj = employee.get("positionId");
//        if (positionIdObj == null) {
//            positionIdObj = employee.get("position_id");
//        }
//
//        Long positionId = null;
//        if (positionIdObj != null) {
//            if (positionIdObj instanceof Integer) {
//                positionId = ((Integer) positionIdObj).longValue();
//            } else if (positionIdObj instanceof Long) {
//                positionId = (Long) positionIdObj;
//            } else if (positionIdObj instanceof String) {
//                positionId = Long.parseLong((String) positionIdObj);
//            }
//        }
//
//        // Get position details and fees
//        BigDecimal basicSalary = BigDecimal.ZERO;
//        String positionName = "";
//        PositionFeesDTO positionFees = null;
//
//        if (positionId != null) {
//            Map<String, Object> position = getPositionById(positionId);
//            if (position != null) {
//                Object salaryObj = position.get("basicSalary");
//                if (salaryObj == null) {
//                    salaryObj = position.get("basic_salary");
//                }
//                if (salaryObj != null) {
//                    if (salaryObj instanceof Integer) {
//                        basicSalary = BigDecimal.valueOf((Integer) salaryObj);
//                    } else if (salaryObj instanceof Double) {
//                        basicSalary = BigDecimal.valueOf((Double) salaryObj);
//                    } else if (salaryObj instanceof BigDecimal) {
//                        basicSalary = (BigDecimal) salaryObj;
//                    } else if (salaryObj instanceof String) {
//                        basicSalary = new BigDecimal((String) salaryObj);
//                    }
//                }
//
//                positionName = (String) position.get("positionName");
//                if (positionName == null) {
//                    positionName = (String) position.get("position_name");
//                }
//            }
//
//            positionFees = getPositionFees(positionId);
//        }
//
//        // Check if payroll already exists (for editing)
//        Payroll existingPayroll = payrollRepository
//                .findByEmpIdAndPayrollMonth(empId, payrollMonth)
//                .orElse(null);
//
//        return PayrollFormDTO.builder()
//                .empId(empId)
//                .empName((String) employee.get("empName"))
//                .deptId(deptId)
//                .deptName((String) employee.get("deptName"))
//                .positionId(positionId)
//                .positionName(positionName)
//                .basicSalary(basicSalary)
//                .departmentFees(deptFees != null ? deptFees.getFeesAmount() : BigDecimal.ZERO)
//                .positionFees(positionFees != null ? positionFees.getFeesAmount() : BigDecimal.ZERO)
//                .bonusAmount(existingPayroll != null ? existingPayroll.getBonusAmount() : BigDecimal.ZERO)
//                .otherAllowance(existingPayroll != null ? existingPayroll.getOtherAllowance() : BigDecimal.ZERO)
//                .regularAttendFees(existingPayroll != null ? existingPayroll.getRegularAttendFees() : BigDecimal.ZERO)
//                .deductions(existingPayroll != null ? existingPayroll.getDeductions() : BigDecimal.ZERO)
//                .payrollMonth(payrollMonth)
//                .build();
//    }


    public PayrollFormDTO getPayrollFormData(String empId, String payrollMonth) {
        log.debug("Getting payroll form data for empId: {}, month: {}", empId, payrollMonth);

        // Get employee details from API
        Map<String, Object> employee = getEmployeeById(empId);
        if (employee == null) {
            throw new RuntimeException("Employee not found with ID: " + empId);
        }

        // Get department fees from database
        String deptId = (String) employee.get("deptId");
        if (deptId == null) {
            deptId = (String) employee.get("dept_id");
        }

        DepartmentFeesDTO deptFees = deptId != null ? getDepartmentFees(deptId) : null;

        // Get position ID
        Object positionIdObj = employee.get("positionId");
        if (positionIdObj == null) {
            positionIdObj = employee.get("position_id");
        }

        Long positionId = null;
        String positionName = "";
        BigDecimal basicSalary = BigDecimal.ZERO;
        PositionFeesDTO positionFees = null;

        if (positionIdObj != null) {
            if (positionIdObj instanceof Integer) {
                positionId = ((Integer) positionIdObj).longValue();
            } else if (positionIdObj instanceof Long) {
                positionId = (Long) positionIdObj;
            } else if (positionIdObj instanceof String) {
                positionId = Long.parseLong((String) positionIdObj);
            }

            // Get position fees from database
            if (positionId != null) {
                positionFees = getPositionFees(positionId);

                // Get position details from API for name
                Map<String, Object> position = getPositionById(positionId);
                if (position != null) {
                    positionName = (String) position.get("positionName");
                    if (positionName == null) {
                        positionName = (String) position.get("position_name");
                    }
                }
            }
        }

        // Get basic salary from position fees if available
        if (positionFees != null && positionFees.getBasicSalary() != null) {
            basicSalary = positionFees.getBasicSalary();
        }

        // Get department name from departments API or from employee
        String deptName = (String) employee.get("deptName");
        if (deptName == null) {
            deptName = (String) employee.get("dept_name");
        }


        if (deptName == null && deptId != null) {
            try {
                Map<String, Object> dept = restTemplate.getForObject(DEPT_API + "/" + deptId, Map.class);
                if (dept != null) {
                    deptName = (String) dept.get("deptName");
                    if (deptName == null) {
                        deptName = (String) dept.get("dept_name");
                    }
                }
            } catch (Exception e) {
                log.error("Error fetching department name for ID: {}", deptId);
            }
        }

        // Check if payroll already exists (for editing)
        Payroll existingPayroll = payrollRepository
                .findByEmpIdAndPayrollMonth(empId, payrollMonth)
                .orElse(null);

        // Build and return the DTO with all fields
        return PayrollFormDTO.builder()
                .empId(empId)
                .empName((String) employee.get("empName"))
                .deptId(deptId)
                .deptName(deptName != null ? deptName : "")
                .positionId(positionId)
                .positionName(positionName != null ? positionName : "")
                .basicSalary(basicSalary)
                .departmentFees(deptFees != null ? deptFees.getFeesAmount() : BigDecimal.ZERO)
                .positionFees(positionFees != null ? positionFees.getFeesAmount() : BigDecimal.ZERO)
                .bonusAmount(existingPayroll != null ? existingPayroll.getBonusAmount() : BigDecimal.ZERO)
                .otherAllowance(existingPayroll != null ? existingPayroll.getOtherAllowance() : BigDecimal.ZERO)
                .regularAttendFees(existingPayroll != null ? existingPayroll.getRegularAttendFees() : BigDecimal.ZERO)
                .deductions(existingPayroll != null ? existingPayroll.getDeductions() : BigDecimal.ZERO)
                .payrollMonth(payrollMonth)
                .build();
    }



    // Save or update payroll
    @Transactional
    public PayrollDTO savePayroll(PayrollFormDTO formDTO) {
        log.debug("Saving payroll for empId: {}, month: {}", formDTO.getEmpId(), formDTO.getPayrollMonth());

        Payroll payroll = payrollRepository
                .findByEmpIdAndPayrollMonth(formDTO.getEmpId(), formDTO.getPayrollMonth())
                .orElse(new Payroll());

        // Calculate totals including position fees
        BigDecimal totalEarnings = formDTO.getBasicSalary()
                .add(formDTO.getDepartmentFees() != null ? formDTO.getDepartmentFees() : BigDecimal.ZERO)
                .add(formDTO.getPositionFees() != null ? formDTO.getPositionFees() : BigDecimal.ZERO)
                .add(formDTO.getBonusAmount() != null ? formDTO.getBonusAmount() : BigDecimal.ZERO)
                .add(formDTO.getOtherAllowance() != null ? formDTO.getOtherAllowance() : BigDecimal.ZERO)
                .add(formDTO.getRegularAttendFees() != null ? formDTO.getRegularAttendFees() : BigDecimal.ZERO);

        BigDecimal deductions = formDTO.getDeductions() != null ? formDTO.getDeductions() : BigDecimal.ZERO;
        BigDecimal netSalary = totalEarnings.subtract(deductions);

        // Set payroll data
        payroll.setEmpId(formDTO.getEmpId());
        payroll.setEmpName(formDTO.getEmpName());
        payroll.setDeptId(formDTO.getDeptId());
        payroll.setDeptName(formDTO.getDeptName());
        payroll.setPositionId(formDTO.getPositionId());
        payroll.setPositionName(formDTO.getPositionName());
        payroll.setPayrollMonth(formDTO.getPayrollMonth());
        payroll.setBasicSalary(formDTO.getBasicSalary());
        payroll.setDepartmentFees(formDTO.getDepartmentFees());
        payroll.setPositionFees(formDTO.getPositionFees());
        payroll.setBonusAmount(formDTO.getBonusAmount());
        payroll.setOtherAllowance(formDTO.getOtherAllowance());
        payroll.setRegularAttendFees(formDTO.getRegularAttendFees());
        payroll.setDeductions(formDTO.getDeductions());
        payroll.setTotalEarnings(totalEarnings);
        payroll.setNetSalary(netSalary);
        payroll.setStatus(payroll.getId() == null ? PayrollStatus.DRAFT : payroll.getStatus());
        payroll.setUpdatedAt(LocalDateTime.now());

        if (payroll.getId() == null) {
            payroll.setCreatedAt(LocalDateTime.now());
        }

        payrollRepository.save(payroll);
        return convertToDTO(payroll);
    }

    // Get payroll by ID for editing
    public PayrollDTO getPayrollById(Long id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found with ID: " + id));
        return convertToDTO(payroll);
    }

    // Update payroll (edit)
    @Transactional
    public PayrollDTO updatePayroll(Long id, PayrollFormDTO formDTO) {
        log.debug("Updating payroll with id: {}", id);

        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found with ID: " + id));

        // Update editable fields
        payroll.setBonusAmount(formDTO.getBonusAmount() != null ? formDTO.getBonusAmount() : BigDecimal.ZERO);
        payroll.setOtherAllowance(formDTO.getOtherAllowance() != null ? formDTO.getOtherAllowance() : BigDecimal.ZERO);
        payroll.setRegularAttendFees(formDTO.getRegularAttendFees() != null ? formDTO.getRegularAttendFees() : BigDecimal.ZERO);
        payroll.setDeductions(formDTO.getDeductions() != null ? formDTO.getDeductions() : BigDecimal.ZERO);

        // Recalculate totals
        BigDecimal totalEarnings = payroll.getBasicSalary()
                .add(payroll.getDepartmentFees() != null ? payroll.getDepartmentFees() : BigDecimal.ZERO)
                .add(payroll.getPositionFees() != null ? payroll.getPositionFees() : BigDecimal.ZERO)
                .add(payroll.getBonusAmount() != null ? payroll.getBonusAmount() : BigDecimal.ZERO)
                .add(payroll.getOtherAllowance() != null ? payroll.getOtherAllowance() : BigDecimal.ZERO)
                .add(payroll.getRegularAttendFees() != null ? payroll.getRegularAttendFees() : BigDecimal.ZERO);

        BigDecimal netSalary = totalEarnings.subtract(payroll.getDeductions() != null ? payroll.getDeductions() : BigDecimal.ZERO);

        payroll.setTotalEarnings(totalEarnings);
        payroll.setNetSalary(netSalary);
        payroll.setUpdatedAt(LocalDateTime.now());

        payrollRepository.save(payroll);
        return convertToDTO(payroll);
    }

    // Get all payrolls for a month
    public List<PayrollDTO> getPayrollsByMonth(String payrollMonth) {
        return payrollRepository.findByPayrollMonthOrderByEmpNameAsc(payrollMonth)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get employee payroll history
    public List<PayrollDTO> getEmployeePayrollHistory(String empId) {
        return payrollRepository.findByEmpIdOrderByPayrollMonthDesc(empId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Update payroll status
    @Transactional
    public PayrollDTO updatePayrollStatus(Long id, PayrollStatus status) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        payroll.setStatus(status);
        payroll.setUpdatedAt(LocalDateTime.now());
        payrollRepository.save(payroll);

        return convertToDTO(payroll);
    }

    // Delete payroll
    @Transactional
    public void deletePayroll(Long id) {
        payrollRepository.deleteById(id);
    }

    // Helper methods
    private Map<String, Object> getEmployeeById(String empId) {
        try {
            return restTemplate.getForObject(EMP_API + "/" + empId, Map.class);
        } catch (Exception e) {
            log.error("Error fetching employee {}: {}", empId, e.getMessage());
            return null;
        }
    }

    private Map<String, Object> getPositionById(Object positionId) {
        try {
            return restTemplate.getForObject(POS_API + "/" + positionId, Map.class);
        } catch (Exception e) {
            log.error("Error fetching position {}: {}", positionId, e.getMessage());
            return null;
        }
    }

    private DepartmentFeesDTO convertToDTO(DepartmentFees fees) {
        return DepartmentFeesDTO.builder()
                .deptId(fees.getDeptId())
                .deptName(fees.getDeptName())
                .feesAmount(fees.getFeesAmount())
                .description(fees.getDescription())
                .build();
    }

//    private PositionFeesDTO convertToDTO(PositionFees fees) {
//        return PositionFeesDTO.builder()
//                .positionId(fees.getPositionId())
//                .positionName(fees.getPositionName())
//                .feesAmount(fees.getFeesAmount())
//                .description(fees.getDescription())
//                .build();
//    }

    private PositionFeesDTO convertToDTO(PositionFees fees) {
        return PositionFeesDTO.builder()
                .positionId(fees.getPositionId())
                .positionName(fees.getPositionName())
                .basicSalary(fees.getBasicSalary())  // Add this line
                .feesAmount(fees.getFeesAmount())
                .description(fees.getDescription())
                .build();
    }

    private PayrollDTO convertToDTO(Payroll payroll) {
        return PayrollDTO.builder()
                .id(payroll.getId())
                .empId(payroll.getEmpId())
                .empName(payroll.getEmpName())
                .deptId(payroll.getDeptId())
                .deptName(payroll.getDeptName())
                .positionName(payroll.getPositionName())
                .payrollMonth(payroll.getPayrollMonth())
                .basicSalary(payroll.getBasicSalary())
                .departmentFees(payroll.getDepartmentFees())
                .positionFees(payroll.getPositionFees())
                .bonusAmount(payroll.getBonusAmount())
                .otherAllowance(payroll.getOtherAllowance())
                .regularAttendFees(payroll.getRegularAttendFees())
                .deductions(payroll.getDeductions())
                .totalEarnings(payroll.getTotalEarnings())
                .netSalary(payroll.getNetSalary())
                .status(payroll.getStatus() != null ? payroll.getStatus().toString() : "DRAFT")
                .build();
    }


}
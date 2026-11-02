package com.humanresource.humanresourcemanagement.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "resign_employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResignEmployee {

    @Id
    private String empID;

    private String photo;

    @Column(nullable = false)
    private String empName;

    @Column(name = "dept_id")
    private String deptId;

    @Column(name = "position_id")
    private String positionId;

    @Column(unique = true, nullable = false)
    private String nrc;

    private Integer age;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "phone_no")
    private String phoneNo;

    private String address;

    @Column(name = "father_name")
    private String fatherName;

    private LocalDate dob;

    private String gender;

    private LocalDate resignedDate;


}

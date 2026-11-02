//package com.humanresource.humanresourcemanagement.entities;
//
//
//
//import jakarta.persistence.*;
//
//import lombok.*;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "employees")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Employee {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long empID;
//
//    @Column(name = "photo")
//    private String photo;
//
//    @Lob
//    @Column(name = "photo", columnDefinition = "LONGBLOB")
//    private byte[] photo;
//
//
//    @Column(nullable = false)
//    private String empName;
//
//    @Column(name = "dept_id")
//    private String deptId;
//
//    @Column(name = "position_id")
//    private String positionId;
//
//    @Column(unique = true, nullable = false)
//    private String nrc;
//
//    private Integer age;
//
//    @Column(name = "join_date")
//    private LocalDate joinDate;
//
//    @Column(name = "phone_no")
//    private String phoneNo;
//
//    private String address;
//
//    @Column(name = "father_name")
//    private String fatherName;
//
//    private LocalDate dob;
//
//    private String gender;
//}

package com.humanresource.humanresourcemanagement.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {



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
}
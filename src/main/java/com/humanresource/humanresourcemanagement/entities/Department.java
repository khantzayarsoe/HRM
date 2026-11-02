package com.humanresource.humanresourcemanagement.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptID;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "description")
    private String desc;


}

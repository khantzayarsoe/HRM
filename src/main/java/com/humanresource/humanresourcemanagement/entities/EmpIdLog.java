package com.humanresource.humanresourcemanagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empIdLog")
public class EmpIdLog {

    @Id
    private String empID;
}

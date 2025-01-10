package com.AMS.attendance.entities;

import jakarta.persistence.*;
import lombok.Data;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class MonthlyLeaveRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


    private LocalDate date;
    private String month;// Represents the month and year
    private int year;

    private String leaveType; // Type of leave (optional)

    private int leavesTaken; // Number of leaves taken
}

package com.AMS.attendance.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MonthlyAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Employee employee;
    private String month;
    private int year;
    private int daysPresent;
    private int daysAbsent; // Ensure this field is present



}


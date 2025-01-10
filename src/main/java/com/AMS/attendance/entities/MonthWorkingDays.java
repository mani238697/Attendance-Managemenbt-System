package com.AMS.attendance.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MonthWorkingDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String monthName;
    private int totalDays; // Total number of days in the month
    private int workingDays; // Number of working days after removing holidays
    private int year; // Add year field
}

package com.AMS.attendance.dtos;

import lombok.Data;

@Data
public class MonthlyAttendanceDTO {
    private Long id;
    private String month;
    private int year;
    private long daysPresent;
    private long daysAbsent;
    private String employeeName; // Add employee name

    // Constructor, getters, and setters if needed
}

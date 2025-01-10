package com.AMS.attendance.dtos;

import com.AMS.attendance.entities.TimeRecord;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class TimeRecordDTO {

    private Long id;
    private Long employeeId;
    private LocalDate date;
    private LocalTime clockInTime;
    private LocalTime clockOutTime;
    private LocalTime breakInTime;
    private LocalTime breakOutTime;
    private String duration;
    private String breakTime;



    public TimeRecordDTO(TimeRecord timeRecord) {
        this.id = timeRecord.getId();
        this.employeeId = timeRecord.getEmployee().getId();
        this.date = timeRecord.getDate();
        this.clockInTime = timeRecord.getClockInTime();
        this.clockOutTime = timeRecord.getClockOutTime();
        this.breakInTime = timeRecord.getBreakInTime();
        this.breakOutTime = timeRecord.getBreakOutTime();
        this.duration = timeRecord.getTotalDuration();
        this.breakTime = timeRecord.getTotalBreakTime();
    }
}


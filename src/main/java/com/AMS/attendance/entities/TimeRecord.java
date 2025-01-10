package com.AMS.attendance.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    private LocalDate date;
    private LocalTime clockInTime;
    private LocalTime clockOutTime;
    private LocalTime breakInTime;
    private LocalTime breakOutTime;

    // New fields to store calculated values
    private Duration breakTime;  // Store break time
    private Duration duration;   // Store total duration

    @PrePersist
    @PreUpdate
    private void calculateDurations() {
        if (breakInTime != null && breakOutTime != null) {
            this.breakTime = Duration.between(breakInTime, breakOutTime);
        } else {
            this.breakTime = Duration.ZERO;
        }

        if (clockInTime != null && clockOutTime != null) {
            this.duration = Duration.between(clockInTime, clockOutTime).minus(this.breakTime);
        } else {
            this.duration = Duration.ZERO;
        }
    }


    @Transient
    public String getTotalBreakTime() {
        return formatDuration(this.breakTime);
    }

    @Transient
    public String getTotalDuration() {
        return formatDuration(this.duration);
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%d hours %d minutes %d seconds", hours, minutes, seconds);
    }
}

package com.AMS.attendance.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
@Component
@ConfigurationProperties(prefix = "working.hours")
@Data
public class WorkingHoursConfig {
    private LocalTime start;
    private LocalTime end;


}
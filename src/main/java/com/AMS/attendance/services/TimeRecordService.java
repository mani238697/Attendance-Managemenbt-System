package com.AMS.attendance.services;

import com.AMS.attendance.configurations.WorkingHoursConfig;
import com.AMS.attendance.dtos.TimeRecordDTO;
import com.AMS.attendance.entities.Employee;
import com.AMS.attendance.entities.TimeRecord;
import com.AMS.attendance.repositories.EmployeeRepository;
import com.AMS.attendance.repositories.TimeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeRecordService {

    @Autowired
    private TimeRecordRepository timeRecordRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WorkingHoursConfig workingHoursConfig;

    public TimeRecord clockIn(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        TimeRecord timeRecord = new TimeRecord();
        timeRecord.setEmployee(employee);
        timeRecord.setDate(LocalDate.now());
        timeRecord.setClockInTime(LocalTime.now());

        return timeRecordRepository.save(timeRecord);
    }

    public TimeRecord clockOut(Long employeeId) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByEmployeeIdAndClockOutTimeIsNull(employeeId);
        if (timeRecords.isEmpty()) {
            throw new RuntimeException("No active clock-in found for employee ID: " + employeeId);
        }

        TimeRecord timeRecord = timeRecords.get(0);
        timeRecord.setClockOutTime(LocalTime.now());
        timeRecord = timeRecordRepository.save(timeRecord);

        updateDaysPresent(employeeId);

        return timeRecord;
    }

    public TimeRecord breakIn(Long employeeId) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByEmployeeIdAndClockOutTimeIsNull(employeeId);
        if (timeRecords.isEmpty()) {
            throw new RuntimeException("No active clock-in found for employee ID: " + employeeId);
        }

        TimeRecord timeRecord = timeRecords.get(0);
        timeRecord.setBreakInTime(LocalTime.now());

        return timeRecordRepository.save(timeRecord);
    }

    public TimeRecord breakOut(Long employeeId) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByEmployeeIdAndClockOutTimeIsNull(employeeId);
        if (timeRecords.isEmpty()) {
            throw new RuntimeException("No active clock-in found for employee ID: " + employeeId);
        }

        TimeRecord timeRecord = timeRecords.get(0);
        timeRecord.setBreakOutTime(LocalTime.now());

        return timeRecordRepository.save(timeRecord);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void autoClockOut() {
        LocalDateTime midnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<TimeRecord> timeRecords = timeRecordRepository.findAll();

        for (TimeRecord timeRecord : timeRecords) {
            if (timeRecord.getClockOutTime() == null) {
                timeRecord.setClockOutTime(LocalTime.of(midnight.getHour(), midnight.getMinute(), midnight.getSecond()));
                timeRecordRepository.save(timeRecord);
            }
        }
    }

    public List<TimeRecord> getTimeRecords(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        return employee.getTimeRecords();
    }

    public String getClockInToClockOutDurationByEmployeeId(Long employeeId) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByEmployeeIdAndClockOutTimeIsNotNull(employeeId);
        if (timeRecords.isEmpty()) {
            throw new RuntimeException("No completed clock-in found for employee ID: " + employeeId);
        }

        // Assuming you want the most recent record
        TimeRecord timeRecord = timeRecords.get(timeRecords.size() - 1);
        if (timeRecord.getClockInTime() != null && timeRecord.getClockOutTime() != null) {
            Duration duration = Duration.between(timeRecord.getClockInTime(), timeRecord.getClockOutTime());
            return formatDuration(duration);
        }
        return "Clock-out time is not available.";
    }

    public String getBreakInToBreakOutDurationByEmployeeId(Long employeeId) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByEmployeeIdAndClockOutTimeIsNull(employeeId);
        if (timeRecords.isEmpty()) {
            throw new RuntimeException("No active clock-in found for employee ID: " + employeeId);
        }

        TimeRecord timeRecord = timeRecords.get(0);
        if (timeRecord.getBreakInTime() != null && timeRecord.getBreakOutTime() != null) {
            // Calculate break duration using stored values
            Duration duration = Duration.between(timeRecord.getBreakInTime(), timeRecord.getBreakOutTime());
            return formatDuration(duration);
        }
        return "Break-out time is not available.";
    }
    public String getClockInToClockOutDurationByEmployeeIdAndDate(Long employeeId, LocalDate date) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByEmployeeIdAndDateAndClockOutTimeIsNotNull(employeeId, date);
        if (timeRecords.isEmpty()) {
            throw new RuntimeException("No completed clock-in found for employee ID: " + employeeId + " on date: " + date);
        }

        // Assuming you want the most recent record for the date
        TimeRecord timeRecord = timeRecords.get(timeRecords.size() - 1);
        if (timeRecord.getClockInTime() != null && timeRecord.getClockOutTime() != null) {
            Duration duration = Duration.between(timeRecord.getClockInTime(), timeRecord.getClockOutTime());
            return formatDuration(duration);
        }
        return "Clock-out time is not available.";
    }

    public String getBreakInToBreakOutDurationByEmployeeIdAndDate(Long employeeId, LocalDate date) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByEmployeeIdAndDateAndBreakOutTimeIsNotNull(employeeId, date);
        if (timeRecords.isEmpty()) {
            throw new RuntimeException("No completed break found for employee ID: " + employeeId + " on date: " + date);
        }

        // Assuming you want the most recent record for the date
        TimeRecord timeRecord = timeRecords.get(timeRecords.size() - 1);
        if (timeRecord.getBreakInTime() != null && timeRecord.getBreakOutTime() != null) {
            Duration duration = Duration.between(timeRecord.getBreakInTime(), timeRecord.getBreakOutTime());
            return formatDuration(duration);
        }
        return "Break-out time is not available.";
    }
    public List<TimeRecordDTO> getAllEmployeeDataByDate(LocalDate date) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByDate(date);
        return timeRecords.stream()
                .map(TimeRecordDTO::new)
                .collect(Collectors.toList());
    }



    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%d hours %d minutes %d seconds", hours, minutes, seconds);
    }

    public long getDaysPresent(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        List<TimeRecord> timeRecords = employee.getTimeRecords();
        Duration requiredWorkingHours = Duration.between(workingHoursConfig.getStart(), workingHoursConfig.getEnd());

        long daysPresent = timeRecords.stream()
                .map(TimeRecord::getDuration)
                .filter(duration -> duration != null && duration.compareTo(requiredWorkingHours) >= 0)
                .count();

        employee.setDaysPresent(daysPresent);
        employeeRepository.save(employee);

        return daysPresent;
    }

    private void updateDaysPresent(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        long daysPresent = getDaysPresent(employeeId);
        employee.setDaysPresent(daysPresent);
        employeeRepository.save(employee);
    }
}

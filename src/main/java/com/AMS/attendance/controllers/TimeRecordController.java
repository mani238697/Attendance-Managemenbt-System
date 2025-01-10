package com.AMS.attendance.controllers;

import com.AMS.attendance.dtos.TimeRecordDTO;
import com.AMS.attendance.entities.Employee;
import com.AMS.attendance.entities.TimeRecord;
import com.AMS.attendance.services.EmployeeService;
import com.AMS.attendance.services.TimeRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/timerecords")
@CrossOrigin(origins = "*")
public class TimeRecordController {

    @Autowired
    private TimeRecordService timeRecordService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/clockin/{employeeId}")
    public TimeRecord clockIn(@PathVariable Long employeeId) {
        return timeRecordService.clockIn(employeeId);
    }

    @PostMapping("/clockout/{employeeId}")
    public ResponseEntity<TimeRecord> clockOut(@PathVariable Long employeeId) {
        try {
            TimeRecord timeRecord = timeRecordService.clockOut(employeeId);
            return ResponseEntity.ok(timeRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/breakin/{employeeId}")
    public ResponseEntity<TimeRecord> breakIn(@PathVariable Long employeeId) {
        try {
            TimeRecord timeRecord = timeRecordService.breakIn(employeeId);
            return ResponseEntity.ok(timeRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/breakout/{employeeId}")
    public ResponseEntity<TimeRecord> breakOut(@PathVariable Long employeeId) {
        try {
            TimeRecord timeRecord = timeRecordService.breakOut(employeeId);
            return ResponseEntity.ok(timeRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<List<TimeRecord>> getTimeRecords(@PathVariable Long employeeId) {
        List<TimeRecord> timeRecords = timeRecordService.getTimeRecords(employeeId);
        return ResponseEntity.ok(timeRecords);
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/duration/clockin-to-clockout/employee/{employeeId}")
    public ResponseEntity<String> getClockInToClockOutDurationByEmployeeId(@PathVariable Long employeeId) {
        try {
            String duration = timeRecordService.getClockInToClockOutDurationByEmployeeId(employeeId);
            return ResponseEntity.ok(duration);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/duration/breakin-to-breakout/employee/{employeeId}")
    public ResponseEntity<String> getBreakInToBreakOutDurationByEmployeeId(@PathVariable Long employeeId) {
        try {
            String duration = timeRecordService.getBreakInToBreakOutDurationByEmployeeId(employeeId);
            return ResponseEntity.ok(duration);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/duration/clockin-to-clockout/employee/{employeeId}/date/{date}")
    public ResponseEntity<String> getClockInToClockOutDurationByEmployeeIdAndDate(
            @PathVariable Long employeeId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            String duration = timeRecordService.getClockInToClockOutDurationByEmployeeIdAndDate(employeeId, date);
            return ResponseEntity.ok(duration);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/duration/breakin-to-breakout/employee/{employeeId}/date/{date}")
    public ResponseEntity<String> getBreakInToBreakOutDurationByEmployeeIdAndDate(
            @PathVariable Long employeeId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            String duration = timeRecordService.getBreakInToBreakOutDurationByEmployeeIdAndDate(employeeId, date);
            return ResponseEntity.ok(duration);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/all/employee-data/date/{date}")
    public ResponseEntity<List<TimeRecordDTO>> getAllEmployeeDataByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<TimeRecordDTO> employeeData = timeRecordService.getAllEmployeeDataByDate(date);
            return ResponseEntity.ok(employeeData);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployee(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/employees/{id}/days-present")
    public ResponseEntity<Long> getDaysPresent(@PathVariable Long id) {
        try {
            long daysPresent = timeRecordService.getDaysPresent(id);
            return ResponseEntity.ok(daysPresent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}


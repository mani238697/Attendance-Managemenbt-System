package com.AMS.attendance.controllers;

import com.AMS.attendance.dtos.MonthlyAttendanceDTO;
import com.AMS.attendance.entities.MonthWorkingDays;
import com.AMS.attendance.entities.MonthlyAttendance;
import com.AMS.attendance.repositories.EmployeeRepository;
import com.AMS.attendance.repositories.MonthlyAttendanceRepository;
import com.AMS.attendance.services.AttendanceService;
import com.AMS.attendance.services.MonthWorkingDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-attendance")
public class MonthlyAttendanceController {

    @Autowired
    private MonthlyAttendanceRepository monthlyAttendanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MonthWorkingDaysService monthWorkingDaysService;
    @Autowired
    private AttendanceService monthlyAttendanceService;

    @GetMapping("/employee/{employeeId}/year/{year}")
    public ResponseEntity<List<MonthlyAttendance>> getMonthlyAttendance(
            @PathVariable Long employeeId, @PathVariable int year) {
        List<MonthlyAttendance> monthlyAttendance = monthlyAttendanceRepository.findByEmployeeIdAndYear(employeeId, year);
        return ResponseEntity.ok(monthlyAttendance);
    }
//    @GetMapping("/monthly/{employeeId}/month/{month}/year/{year}")
//    public MonthlyAttendanceDTO getMonthlyAttendance(@PathVariable Long employeeId,
//                                                     @PathVariable String month,
//                                                     @PathVariable int year) {
//        MonthlyAttendance monthlyAttendance = monthlyAttendanceService.getMonthlyAttendance(employeeId, month, year);
//
//        MonthlyAttendanceDTO dto = new MonthlyAttendanceDTO();
//        dto.setId(monthlyAttendance.getId());
//        dto.setMonth(monthlyAttendance.getMonth());
//        dto.setYear(monthlyAttendance.getYear());
//        dto.setDaysPresent(monthlyAttendance.getDaysPresent());
//        dto.setDaysAbsent(monthlyAttendance.getDaysAbsent());
//        dto.setEmployeeName(monthlyAttendance.getEmployee().getName()); // Fetch employee name
//
//        return dto;
//    }
//
//    // Endpoint to get the number of days present in a specific month
//    @GetMapping("/days-present/{employeeId}/month/{month}/year/{year}")
//    public long getDaysPresent(@PathVariable Long employeeId,
//                               @PathVariable String month,
//                               @PathVariable int year) {
//        return monthlyAttendanceService.getDaysPresent(employeeId, month, year);
//    }
//
//    // Endpoint to get the number of days absent in a specific month
//    @GetMapping("/days-absent/{employeeId}/month/{month}/year/{year}")
//    public long getDaysAbsent(@PathVariable Long employeeId,
//                              @PathVariable String month,
//                              @PathVariable int year) {
//        // Retrieve working days for the month and year
//        MonthWorkingDays monthWorkingDays = monthWorkingDaysService.getAllWorkingDays().stream()
//                .filter(mwd -> mwd.getMonthName().equalsIgnoreCase(month) && mwd.getYear() == year)
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Working days data not found for month: " + month + " and year: " + year));
//
//        int workingDays = monthWorkingDays.getWorkingDays();
//        long daysPresent = monthlyAttendanceService.getDaysPresent(employeeId, month, year);
//        long daysAbsent = workingDays - daysPresent;
//
//        return daysAbsent;
//    }
}


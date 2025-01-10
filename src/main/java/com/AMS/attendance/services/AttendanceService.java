package com.AMS.attendance.services;

import com.AMS.attendance.entities.Employee;
import com.AMS.attendance.entities.MonthWorkingDays;
import com.AMS.attendance.entities.MonthlyAttendance;
import com.AMS.attendance.entities.TimeRecord;
import com.AMS.attendance.repositories.EmployeeRepository;
import com.AMS.attendance.repositories.MonthlyAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MonthlyAttendanceRepository monthlyAttendanceRepository;
    @Autowired
    private MonthWorkingDaysService monthWorkingDaysService;
    private Duration requiredWorkingHours = Duration.ofHours(9); // Example: 9 hours required working hours

    public void updateMonthlyAttendance(Long employeeId, String month, int year) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        List<TimeRecord> timeRecords = employee.getTimeRecords();

        // Group time records by date
        Map<LocalDate, List<TimeRecord>> recordsByDate = timeRecords.stream()
                .collect(Collectors.groupingBy(TimeRecord::getDate));

        int daysPresent = 0;

        // Calculate total duration for each day
        for (Map.Entry<LocalDate, List<TimeRecord>> entry : recordsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<TimeRecord> recordsForDay = entry.getValue();

            Duration totalDuration = recordsForDay.stream()
                    .map(TimeRecord::getDuration)
                    .filter(duration -> duration != null)
                    .reduce(Duration.ZERO, Duration::plus);

            if (totalDuration.compareTo(requiredWorkingHours) >= 0) {
                daysPresent++;
            }
        }

        // Retrieve working days for the month and year
        MonthWorkingDays monthWorkingDays = monthWorkingDaysService.getAllWorkingDays().stream()
                .filter(mwd -> mwd.getMonthName().equalsIgnoreCase(month) && mwd.getYear() == year)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Working days data not found for month: " + month + " and year: " + year));

        int workingDays = monthWorkingDays.getWorkingDays();
        int daysAbsent = workingDays - (int) daysPresent;

        MonthlyAttendance monthlyAttendance = new MonthlyAttendance();
        monthlyAttendance.setEmployee(employee);
        monthlyAttendance.setMonth(month);
        monthlyAttendance.setYear(year);
        monthlyAttendance.setDaysPresent(daysPresent);
        monthlyAttendance.setDaysAbsent(daysAbsent);

        monthlyAttendanceRepository.save(monthlyAttendance);
    }
//    public MonthlyAttendance getMonthlyAttendance(Long employeeId, String month, int year) {
//        return monthlyAttendanceRepository.findByEmployeeIdAndMonthAndYear(employeeId, month, year)
//                .orElseThrow(() -> new RuntimeException(
//                        "Monthly attendance not found for employee ID: "
//                                + employeeId + ", month: " + month + ", year: " + year));
//    }
//
//    public long getDaysPresent(Long employeeId, String month, int year) {
//        MonthlyAttendance monthlyAttendance = getMonthlyAttendance(employeeId, month, year);
//        return monthlyAttendance.getDaysPresent();
//    }
}

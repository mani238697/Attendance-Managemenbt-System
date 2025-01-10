package com.AMS.attendance.services;

import com.AMS.attendance.entities.Holiday;
import com.AMS.attendance.entities.MonthWorkingDays;
import com.AMS.attendance.repositories.HolidayRepository;
import com.AMS.attendance.repositories.MonthWorkingDaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class MonthWorkingDaysService {

    @Autowired
    private MonthWorkingDaysRepository monthWorkingDaysRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    public void populateWorkingDaysForYear(int year) {
        for (Month month : Month.values()) {
            LocalDate monthStart = LocalDate.of(year, month, 1);
            LocalDate monthEnd = monthStart.with(TemporalAdjusters.lastDayOfMonth());

            int totalDaysInMonth = monthEnd.getDayOfMonth();
            int workingDays = getWorkingDaysInMonth(monthStart);

            MonthWorkingDays monthWorkingDays = new MonthWorkingDays();
            monthWorkingDays.setMonthName(month.name());
            monthWorkingDays.setTotalDays(totalDaysInMonth);
            monthWorkingDays.setWorkingDays(workingDays);

            monthWorkingDaysRepository.save(monthWorkingDays);
        }
    }

    // Calculate working days excluding holidays
    public int getWorkingDaysInMonth(LocalDate date) {
        LocalDate monthStart = date.withDayOfMonth(1);
        LocalDate monthEnd = date.with(TemporalAdjusters.lastDayOfMonth());

        // Fetch holidays
        List<Holiday> holidays = holidayRepository.findByDateBetween(monthStart, monthEnd);

        // Total days in the month
        int totalDays = monthEnd.getDayOfMonth();

        // Subtract holidays from total days
        int workingDays = totalDays - holidays.size();

        return workingDays;
    }

    public List<MonthWorkingDays> getAllWorkingDays() {
        return monthWorkingDaysRepository.findAll();
    }
}

package com.AMS.attendance.services;

import com.AMS.attendance.entities.Holiday;
import com.AMS.attendance.repositories.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    public void addHolidays(List<Holiday> holidays) {
        holidayRepository.saveAll(holidays);
    }
    public void addHoliday(Holiday holiday) {
        holidayRepository.save(holiday);
    }



    public void deleteHoliday(Long holidayId) {
        holidayRepository.deleteById(holidayId);
    }
    public void deleteHolidaysByDate(LocalDate date) {
        List<Holiday> holidays = holidayRepository.findByDateBetween(date, date);
        holidayRepository.deleteAll(holidays);
    }

    public int getPublicHolidaysCount(LocalDate monthStart, LocalDate monthEnd) {
        List<Holiday> holidays = holidayRepository.findByDateBetween(monthStart, monthEnd);
        return holidays.size();
    }
    public int getTotalDaysInMonth(LocalDate date) {
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        return lastDayOfMonth.getDayOfMonth();
    }

    // Calculate working days in a month, subtracting public holidays
    public int getWorkingDaysInMonth(LocalDate date) {
        LocalDate monthStart = date.withDayOfMonth(1);
        LocalDate monthEnd = date.with(TemporalAdjusters.lastDayOfMonth());

        int totalDaysInMonth = getTotalDaysInMonth(date);
        List<Holiday> holidays = holidayRepository.findByDateBetween(monthStart, monthEnd);

        int holidaysCount = holidays.size();
        int workingDays = totalDaysInMonth - holidaysCount;

        return workingDays;
    }
}

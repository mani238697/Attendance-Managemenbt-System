package com.AMS.attendance.repositories;

import com.AMS.attendance.entities.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday,Long> {
    List<Holiday> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
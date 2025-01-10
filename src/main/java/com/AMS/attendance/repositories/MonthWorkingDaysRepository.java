package com.AMS.attendance.repositories;

import com.AMS.attendance.entities.MonthWorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthWorkingDaysRepository extends JpaRepository<MonthWorkingDays, Long> {
}


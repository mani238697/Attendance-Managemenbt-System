package com.AMS.attendance.repositories;

import com.AMS.attendance.entities.MonthlyAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyAttendanceRepository extends JpaRepository<MonthlyAttendance, Long> {
    List<MonthlyAttendance> findByEmployeeIdAndYear(Long employeeId, int year);

//    <T> ScopedValue<T> findByEmployeeIdAndMonthAndYear(Long employeeId, String month, int year);

    MonthlyAttendance findByEmployeeIdAndMonthAndYear(Long employeeId, String month, int year);
}


package com.AMS.attendance.repositories;

import com.AMS.attendance.entities.Employee;
import com.AMS.attendance.entities.MonthlyLeaveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MonthlyLeaveRecordRepository extends JpaRepository<MonthlyLeaveRecord, Long> {
    List<MonthlyLeaveRecord> findByEmployeeAndMonthAndYear(Employee employee, String month, int year);
    List<MonthlyLeaveRecord> findByDate(LocalDate date);
    List<MonthlyLeaveRecord> findByEmployeeIdAndDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);

}


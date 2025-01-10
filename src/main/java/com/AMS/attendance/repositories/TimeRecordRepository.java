package com.AMS.attendance.repositories;

import com.AMS.attendance.entities.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface TimeRecordRepository extends JpaRepository<TimeRecord,Long> {
    List<TimeRecord> findByEmployeeIdAndClockOutTimeIsNull(Long employeeId);

//    Optional<TimeRecord> findByEmployeeIdAndClockOutTimeIsNotNull(Long employeeId);
    List<TimeRecord> findByEmployeeIdAndClockOutTimeIsNotNull(Long employeeId);
    List<TimeRecord> findByEmployeeIdAndDateAndClockOutTimeIsNotNull(Long employeeId, LocalDate date);
    List<TimeRecord> findByEmployeeIdAndDateAndBreakOutTimeIsNotNull(Long employeeId, LocalDate date);
    Optional<TimeRecord> findByEmployeeIdAndBreakOutTimeIsNotNull(Long employeeId);
    List<TimeRecord> findByDate(LocalDate date);
}


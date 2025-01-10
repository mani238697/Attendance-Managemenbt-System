package com.AMS.attendance.repositories;

import com.AMS.attendance.entities.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    LeaveType findByTypeName(String typeName); // Custom query method
}

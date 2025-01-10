package com.AMS.attendance.services;

import com.AMS.attendance.entities.LeaveType;
import com.AMS.attendance.repositories.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    public LeaveType createLeaveType(LeaveType leaveType) {
        return leaveTypeRepository.save(leaveType);
    }
    public List<LeaveType> createLeaveTypes(List<LeaveType> leaveTypes) {
        return leaveTypeRepository.saveAll(leaveTypes);
    }


    public LeaveType getLeaveTypeById(Long id) {
        return leaveTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LeaveType not found with ID: " + id));
    }

    public LeaveType getLeaveTypeByName(String typeName) {
        return leaveTypeRepository.findByTypeName(typeName);
    }

    public List<LeaveType> getAllLeaveTypes() {
        return leaveTypeRepository.findAll();
    }

    public LeaveType updateLeaveType(Long id, LeaveType leaveTypeDetails) {
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LeaveType not found with ID: " + id));
        leaveType.setTypeName(leaveTypeDetails.getTypeName());
        leaveType.setMaxLeaves(leaveTypeDetails.getMaxLeaves());
        return leaveTypeRepository.save(leaveType);
    }

    public void deleteLeaveType(Long id) {
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LeaveType not found with ID: " + id));
        leaveTypeRepository.delete(leaveType);
    }
}

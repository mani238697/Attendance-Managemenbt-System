package com.AMS.attendance.controllers;
import com.AMS.attendance.entities.LeaveType;
import com.AMS.attendance.services.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leave-types")
public class LeaveTypeController {

    @Autowired
    private LeaveTypeService leaveTypeService;

    @PostMapping
    public ResponseEntity<LeaveType> createLeaveType(@RequestBody LeaveType leaveType) {
        LeaveType createdLeaveType = leaveTypeService.createLeaveType(leaveType);
        return ResponseEntity.ok(createdLeaveType);
    }
    @PostMapping("/bulk")
    public ResponseEntity<List<LeaveType>> addLeaveTypes(@RequestBody List<LeaveType> leaveTypes) {
        List<LeaveType> createdLeaveTypes = leaveTypeService.createLeaveTypes(leaveTypes);
        return ResponseEntity.ok(createdLeaveTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveType> getLeaveTypeById(@PathVariable Long id) {
        LeaveType leaveType = leaveTypeService.getLeaveTypeById(id);
        return ResponseEntity.ok(leaveType);
    }

    @GetMapping("/name/{typeName}")
    public ResponseEntity<LeaveType> getLeaveTypeByName(@PathVariable String typeName) {
        LeaveType leaveType = leaveTypeService.getLeaveTypeByName(typeName);
        return ResponseEntity.ok(leaveType);
    }

    @GetMapping
    public ResponseEntity<List<LeaveType>> getAllLeaveTypes() {
        List<LeaveType> leaveTypes = leaveTypeService.getAllLeaveTypes();
        return ResponseEntity.ok(leaveTypes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveType> updateLeaveType(@PathVariable Long id, @RequestBody LeaveType leaveTypeDetails) {
        LeaveType updatedLeaveType = leaveTypeService.updateLeaveType(id, leaveTypeDetails);
        return ResponseEntity.ok(updatedLeaveType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveType(@PathVariable Long id) {
        leaveTypeService.deleteLeaveType(id);
        return ResponseEntity.noContent().build();
    }
}


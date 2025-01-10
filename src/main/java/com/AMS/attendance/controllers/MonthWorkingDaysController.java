package com.AMS.attendance.controllers;

import com.AMS.attendance.entities.MonthWorkingDays;
import com.AMS.attendance.services.MonthWorkingDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/month-working-days")
public class MonthWorkingDaysController {

    @Autowired
    private MonthWorkingDaysService monthWorkingDaysService;

    @PostMapping("/populate/{year}")
    public void populateWorkingDaysForYear(@PathVariable int year) {
        monthWorkingDaysService.populateWorkingDaysForYear(year);
    }

    @GetMapping("/all")
    public List<MonthWorkingDays> getAllWorkingDays() {
        return monthWorkingDaysService.getAllWorkingDays();
    }
}


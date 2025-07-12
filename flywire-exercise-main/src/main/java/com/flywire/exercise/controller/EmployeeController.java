package com.flywire.exercise.controller;

import com.flywire.exercise.model.Employee;
import com.flywire.exercise.service.EmployeeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService svc;
    public EmployeeController(EmployeeService svc) { this.svc = svc; }

    // returns all active employees sorted by last name
    @GetMapping("/active")
    public List<Employee> active() throws IOException {
        return svc.getAllActive();
    }

    // returns the employee and their direct reports by id
    @GetMapping("/{id}")
    public Map<String,Object> byId(@PathVariable int id) throws IOException {
        return svc.getByIdWithHires(id);
    }

    // returns employees hired between start and end dates (most recent first)
    @GetMapping("/hired")
    public List<Employee> byDate(
            @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") LocalDate start,
            @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") LocalDate end
    ) throws IOException {
        return svc.getHiredInRange(start, end);
    }

    // creates a new employee
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee e) throws IOException {
        return ResponseEntity.ok(svc.create(e));
    }

    // deactivates an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deactivate(@PathVariable int id) throws IOException {
        return ResponseEntity.ok(svc.deactivate(id));
    }
}
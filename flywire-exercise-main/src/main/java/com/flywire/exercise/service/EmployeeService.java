package com.flywire.exercise.service;

import com.flywire.exercise.exception.EmployeeNotFoundException;
import com.flywire.exercise.model.Employee;
import com.flywire.exercise.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository repo;
    public EmployeeService(EmployeeRepository repo) { this.repo = repo; }

    // all active sorted by last name
    public List<Employee> getAllActive() throws IOException {
        return repo.findAll().stream()
                .filter(Employee::isActive)
                .sorted(Comparator.comparing(e -> {
                    String[] parts = e.getName().split(" ");
                    return parts[parts.length-1];
                }))
                .collect(Collectors.toList());
    }

    // by ID and names of direct hires
    public Map<String,Object> getByIdWithHires(int id) throws IOException {
        List<Employee> all = repo.findAll();
        Employee me = all.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("No employee " + id));

        List<String> hires = all.stream()
                .filter(e -> me.getDirectReports().contains(e.getId()))
                .map(Employee::getName)
                .collect(Collectors.toList());

        Map<String,Object> result = new HashMap<>();
        result.put("employee", me);
        result.put("directHires", hires);
        return result;
    }

    // hired in range, desc by date */
    public List<Employee> getHiredInRange(LocalDate start, LocalDate end) throws IOException {
        return repo.findAll().stream()
                .filter(e ->
                        !e.getHireDate().isBefore(start) &&
                                !e.getHireDate().isAfter(end))
                .sorted(Comparator.comparing(Employee::getHireDate).reversed())
                .collect(Collectors.toList());
    }

    // create new
    public Employee create(Employee e) throws IOException {
        List<Employee> all = repo.findAll();
        if (all.stream().anyMatch(x->x.getId()==e.getId()))
            throw new IllegalArgumentException("ID exists");
        if (e.getManagerId()!=null &&
                all.stream().noneMatch(x->x.getId()==e.getManagerId()))
            throw new IllegalArgumentException("Manager not found");
        if (e.getDirectReports()!=null) {
            for (int dr : e.getDirectReports()) {
                if (all.stream().noneMatch(x->x.getId()==dr))
                    throw new IllegalArgumentException("DirectReport "+dr+" missing");
            }
        }
        e.setActive(true);
        all.add(e);
        repo.saveAll(all);
        return e;
    }


    public Employee deactivate(int id) throws IOException {
        List<Employee> all = repo.findAll();
        Employee e = all.stream()
                .filter(x->x.getId()==id)
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("No employee "+id));
        e.setActive(false);
        repo.saveAll(all);
        return e;
    }
}
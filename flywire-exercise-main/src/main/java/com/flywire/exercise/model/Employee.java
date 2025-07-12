package com.flywire.exercise.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public class Employee {
    private int id;
    private String name;
    private String position;
    private Integer managerId;
    private List<Integer> directReports;

    @JsonFormat(pattern="MM/dd/yyyy")
    private LocalDate hireDate;

    private boolean active;

    public Employee() {}


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }
    public List<Integer> getDirectReports() { return directReports; }
    public void setDirectReports(List<Integer> directReports) {
        this.directReports = directReports;
    }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
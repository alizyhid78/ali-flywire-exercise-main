package com.flywire.exercise.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String msg) { super(msg); }
}
package com.flywire.exercise.repository;

import com.flywire.exercise.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules();

    private File dataFile() throws IOException {

        return new ClassPathResource("json/data.json").getFile();
    }

    public synchronized List<Employee> findAll() throws IOException {
        return mapper.readValue(dataFile(),
                new TypeReference<List<Employee>>() {});
    }

    public synchronized void saveAll(List<Employee> list) throws IOException {
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(dataFile(), list);
    }
}
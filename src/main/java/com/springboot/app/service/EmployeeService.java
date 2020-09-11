package com.springboot.app.service;

import com.springboot.app.entity.Employee;
import com.springboot.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployee() {
        return (List<Employee>) employeeRepository.findAll();
    }

    public Employee save(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    //delete all table before read new file
    public void deleteAllEmployee() {
        employeeRepository.deleteAll();
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
}

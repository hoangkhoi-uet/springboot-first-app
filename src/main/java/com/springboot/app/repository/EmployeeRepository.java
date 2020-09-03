package com.springboot.app.repository;

import com.springboot.app.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

//    List<Employee> findByNameContaining(String term);

}

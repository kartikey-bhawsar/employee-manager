package com.infy.employeeManager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.employeeManager.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
}

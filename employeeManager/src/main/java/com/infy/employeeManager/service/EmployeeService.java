package com.infy.employeeManager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infy.employeeManager.dto.EmployeeDTO;
import com.infy.employeeManager.exception.EmployeeManagerException;

@Service
public interface EmployeeService {
	public EmployeeDTO getEmployeeById(long empoyeeId) throws EmployeeManagerException;
	public List<EmployeeDTO> getAllEmployees() throws EmployeeManagerException;
	public Long addEmployee(EmployeeDTO employeeDTO) throws EmployeeManagerException;
	public void removeEmployee(long employeeId) throws EmployeeManagerException;
	public List<EmployeeDTO> findEmployeesByPincode(long pincode) throws EmployeeManagerException;
	public EmployeeDTO updateEmployee(long emplpoyeeId, EmployeeDTO empDto) throws EmployeeManagerException;
	public List<EmployeeDTO> getAllEmployees(int pageNo, int pageSize) throws EmployeeManagerException;
}

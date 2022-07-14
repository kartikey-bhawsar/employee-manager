package com.infy.employeeManager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infy.employeeManager.dto.EmployeeDTO;
import com.infy.employeeManager.exception.EmployeeManagerException;
import com.infy.employeeManager.service.EmployeeServiceImpl;

@CrossOrigin
@RestController
@RequestMapping(value="/employee")
public class EmployeeController {
	@Autowired
	private EmployeeServiceImpl empService;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/getEmployee/{employeeId}")
	public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable long employeeId) throws EmployeeManagerException{
		try {
			EmployeeDTO employeeDto=empService.getEmployeeById(employeeId);
			return new ResponseEntity<>(employeeDto,HttpStatus.OK);
		} catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	
	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees() throws EmployeeManagerException{
		try {
			List<EmployeeDTO> employees=empService.getAllEmployees();
			return new ResponseEntity<>(employees,HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	
	@PostMapping("/addemployee")
	public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws EmployeeManagerException {
		try {
			Long employeeId = empService.addEmployee(employeeDTO);
			String successMessage =environment.getProperty("Controller.INSERT_SUCCESS") + employeeId;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	
	@DeleteMapping("deleteEmployee/{employeeId}")
	public ResponseEntity<String> removeEmployee(@PathVariable long employeeId) throws EmployeeManagerException {
		try {
			empService.removeEmployee(employeeId);
			String successMessage=environment.getProperty("Controller.DELETE_SUCCESS");
			return new ResponseEntity<>(successMessage,HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	
	@GetMapping("pincode/{pincode}")
	public ResponseEntity<List<EmployeeDTO>> findEmployeesByPincode(@PathVariable long pincode) throws EmployeeManagerException {
		try {
			List<EmployeeDTO> employees=empService.findEmployeesByPincode(pincode);
			return new ResponseEntity<>(employees,HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	
	@PutMapping("update/{employeeId}")
	public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable long employeeId, @Valid @RequestBody EmployeeDTO employeeDTO) throws EmployeeManagerException {
		try {
			EmployeeDTO empDto=empService.updateEmployee(employeeId,employeeDTO);
			return new ResponseEntity<>(empDto,HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	
	@GetMapping("pagination/{pageNo}/{pageSize}")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@PathVariable int pageNo, @PathVariable int pageSize) throws EmployeeManagerException {
		try {
			List<EmployeeDTO> employees=empService.getAllEmployees(pageNo,pageSize);
			return new ResponseEntity<>(employees,HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	
}

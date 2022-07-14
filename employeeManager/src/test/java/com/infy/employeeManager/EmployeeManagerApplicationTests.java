package com.infy.employeeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.employeeManager.dto.AddressDTO;
import com.infy.employeeManager.dto.EmployeeDTO;
import com.infy.employeeManager.entity.Employee;
import com.infy.employeeManager.exception.EmployeeManagerException;
import com.infy.employeeManager.repository.EmployeeRepository;
import com.infy.employeeManager.service.EmployeeService;
import com.infy.employeeManager.service.EmployeeServiceImpl;

@SpringBootTest
class EmployeeManagerApplicationTests {
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private EmployeeService employeeService=new EmployeeServiceImpl();

	@Test
	public void getEmployeeByIdValidTest() throws Exception {
		Employee e=new Employee();
		AddressDTO a=new AddressDTO(47,"Gunj road","Bhopal",456111);
		List<AddressDTO> aList=new ArrayList<>();
		aList.add(a);
		EmployeeDTO empDto=new EmployeeDTO(13,"Ram","Sharma",aList);
		e=empDto.DtoToEntity();
		Optional<Employee>optional=Optional.of(e);
		Mockito.when(employeeRepository.findById((long) 13)).thenReturn(optional);
		Assertions.assertEquals(empDto.toString(), employeeService.getEmployeeById(13).toString());
	}
	
	@Test
	public void getEmployeeByIdInValidTest() throws Exception {
		Employee e=new Employee();
		AddressDTO a=new AddressDTO(47,"Gunj road","Bhopal",456111);
		List<AddressDTO> aList=new ArrayList<>();
		aList.add(a);
		EmployeeDTO empDto=new EmployeeDTO(13,"Ram","Sharma",aList);
		e=empDto.DtoToEntity();
		Optional<Employee>optional=Optional.of(e);
		Mockito.when(employeeRepository.findById((long) 2)).thenReturn(null);
		Exception exception=Assertions.assertThrows(EmployeeManagerException.class, ()->employeeService.getEmployeeById(13));
		Assertions.assertEquals("Service.EMPLOYEE_NOT_FOUND", exception.getMessage());
	}

}

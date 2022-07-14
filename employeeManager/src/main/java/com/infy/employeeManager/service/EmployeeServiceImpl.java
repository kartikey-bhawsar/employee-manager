package com.infy.employeeManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.employeeManager.dto.EmployeeDTO;
import com.infy.employeeManager.entity.Address;
import com.infy.employeeManager.entity.Employee;
import com.infy.employeeManager.exception.EmployeeManagerException;
import com.infy.employeeManager.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	EmployeeRepository empRepo;
	
	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;
	
//	@PostConstruct
//	public void initDb() {
//		int totalObjects = 20;
//
//        long start = System.currentTimeMillis();
//        List<Employee> employees = new ArrayList<>();
//        for(int i=0;i<totalObjects;i++)
//        {
//        	List<Address> addresses=new ArrayList<>();
//        	for(int j=1;j<=3;j++)
//        	{
//        		addresses.add(new Address("street"+i+j,"city"+i+j,ThreadLocalRandom.current().nextInt(100000, 1000000)));	
//        	}
//        	Employee e=new Employee("User"+i,"user"+i,addresses);
//        	employees.add(e);
//        }		
//
//        System.out.println("Finished creating "+totalObjects+" objects in memory in:" + (System.currentTimeMillis() - start)/1000);
//
//        start = System.currentTimeMillis();
//        System.out.println("Inserting ..........");
//
//        for (int i = 0; i < totalObjects; i += batchSize) {
//            if( i+ batchSize > totalObjects){
//                List<Employee> employees1 = employees.subList(i, totalObjects - 1);
//                empRepo.saveAll(employees1);
//                break;
//            }
//            List<Employee> employees1 = employees.subList(i, i + batchSize);
//            empRepo.saveAll(employees1);
//        }
//
//        System.out.println("Finished inserting "+totalObjects+" objects in :" + (System.currentTimeMillis() - start));
//	}
	
	@Override
	@Cacheable(value="1st-level-cache", key="'EmployeeCache'+#employeeId")
	public EmployeeDTO getEmployeeById(long employeeId) throws EmployeeManagerException
	{
		Optional<Employee>optional=empRepo.findById(employeeId);
		Employee emp=optional.orElseThrow(()-> new EmployeeManagerException("Service.EMPLOYEE_NOT_FOUND"));
		EmployeeDTO empDto=emp.entityToDto();
		empDto.setEmployeeId(emp.getEmployeeId());
		return empDto;
	}
	
	@Override
	@Caching(cacheable = @Cacheable(value="1st-level-cache", key="'ALL'"),
	        evict = @CacheEvict(value="1st-level-cache", key="'ALL'"))
	public List<EmployeeDTO> getAllEmployees() throws EmployeeManagerException
	{
		Iterable<Employee> employees=empRepo.findAll();
		List<EmployeeDTO> eDTOs=new ArrayList<>();
		employees.forEach(e->{
			EmployeeDTO edto=e.entityToDto();
			eDTOs.add(edto);
		});
		if(eDTOs.isEmpty()) throw new EmployeeManagerException("Service.EMPLOYEES_NOT_FOUND");
		return eDTOs;  
	}
	
	@Override
	@CachePut(value="1st-level-cache", key="'EmployeeCache'+#employeeDTO.getEmployeeId()")
	public Long addEmployee(EmployeeDTO employeeDTO) throws EmployeeManagerException
	{
		Employee employee=employeeDTO.DtoToEntity();
		empRepo.save(employee);
		return employee.getEmployeeId();
	}
	
	@Override
	@CacheEvict(value="1st-level-cache", key="#employeeId")
	public void removeEmployee(long employeeId) throws EmployeeManagerException
	{
		Optional<Employee> optional=empRepo.findById(employeeId);
		Employee emp=optional.orElseThrow(()->new EmployeeManagerException("Service.EMPLOYEE_NOT_FOUND"));
		empRepo.delete(emp);
	}
	
	@Override
	@Cacheable(value="1st-level-cache", key="'EmployeeCache'+#pincode")
	public List<EmployeeDTO> findEmployeesByPincode(long pincode) throws EmployeeManagerException
	{
		Iterable<Employee> employees=empRepo.findAll();
		List<EmployeeDTO> empDtos=new ArrayList<>();
		employees.forEach((e)->{
			e.getAddress().forEach((addr)->{
				if(addr.getPincode()==pincode) {
					EmployeeDTO empDto=e.entityToDto();
					empDtos.add(empDto);
				}
			});
		});
		if(empDtos.isEmpty()) throw new EmployeeManagerException("Service.EMPLOYEES_NOT_FOUND");
//		List<EmployeeDTO> employeeDtos;
//		employeeDtos=employees.stream().map(e->{
//			EmployeeDTO empDto=e.entityToDto();
//			return empDto;
//		}).collect(Collectors.toList());
		
		return empDtos;
	}
	
	@Override
	@CachePut(value="1st-level-cache", key="'EmployeeCache'+#employeeId")
	public EmployeeDTO updateEmployee(long employeeId, EmployeeDTO empDto) throws EmployeeManagerException
	{
		Optional<Employee> optional=empRepo.findById(employeeId);
		Employee emp=optional.orElseThrow(()->new EmployeeManagerException("Service.EMPLOYEE_NOT_FOUND"));
		Employee updatedEmployee=empDto.DtoToEntity();
		emp.setAddress(updatedEmployee.getAddress());
		emp.setFirstName(updatedEmployee.getFirstName());
		emp.setLastName(updatedEmployee.getLastName());
		empRepo.save(emp);
		return emp.entityToDto();
	}
	
	@Override
	public List<EmployeeDTO> getAllEmployees(int pageNo, int pageSize) throws EmployeeManagerException{
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Employee> page=empRepo.findAll(pageable);
		if(page.isEmpty()) throw new EmployeeManagerException("Service.EMPLOYEES_NOT_FOUND");
		List<Employee> employees=page.getContent();
		List<EmployeeDTO> eDTOs;
		eDTOs=employees.stream().map(e -> e.entityToDto()).collect(Collectors.toList());
		return eDTOs;  
	}
}

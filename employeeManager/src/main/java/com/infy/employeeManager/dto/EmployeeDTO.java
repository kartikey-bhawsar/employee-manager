package com.infy.employeeManager.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.infy.employeeManager.entity.Address;
import com.infy.employeeManager.entity.Employee;

public class EmployeeDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private long employeeId;
	@NotEmpty(message = "Please provide first name")
	private String firstName;
	@NotEmpty(message = "Please provide last name")
	private String lastName;
	@Valid
	private List<AddressDTO> address;
	
	public EmployeeDTO() {
		super();
	}
	
	public EmployeeDTO(long employeeId, String firstName, String lastName, List<AddressDTO> address) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<AddressDTO> getAddress() {
		return address;
	}
	public void setAddress(List<AddressDTO> address) {
		this.address = address;
	}
	
	public Employee DtoToEntity()
	{
		Employee employee=new Employee();
		employee.setEmployeeId(this.getEmployeeId());
		employee.setFirstName(this.getFirstName());
		employee.setLastName(this.getLastName());
		List<Address> addresses=new ArrayList<>();
		this.getAddress().forEach(e->{
			Address address=new Address();
			address.setAddrId(e.getAddrId());
			address.setCity(e.getCity());
			address.setPincode(e.getPincode());
			address.setStreet(e.getStreet());
			addresses.add(address);
		});
		employee.setAddress(addresses);
		return employee;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmployeeDTO [employeeId=").append(employeeId).append(", firstName=").append(firstName)
				.append(", lastName=").append(lastName).append(", address=").append(address).append("]");
		return builder.toString();
	}
	
}

package com.infy.employeeManager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.infy.employeeManager.dto.AddressDTO;
import com.infy.employeeManager.dto.EmployeeDTO;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable{
	@Id
	@Column(name="employee_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
	@SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
	private long employeeId;
	

	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="employee_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<Address> address;
	public Employee() {
		
	}
	
	public Employee(String firstName, String lastName, List<Address> address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	
	public Employee(long employeeId, String firstName, String lastName, List<Address> address) {
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
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	
	public EmployeeDTO entityToDto()
	{
		EmployeeDTO empDto=new EmployeeDTO();
		empDto.setEmployeeId(this.getEmployeeId());
		empDto.setFirstName(this.getFirstName());
		empDto.setLastName(this.getLastName());
		List<AddressDTO> addresses=new ArrayList<>();
		this.getAddress().forEach(a->{
			AddressDTO addressDTO = new AddressDTO();
			addressDTO.setAddrId(a.getAddrId());
			addressDTO.setStreet(a.getStreet());
			addressDTO.setCity(a.getCity());
			addressDTO.setPincode(a.getPincode());
			addresses.add(addressDTO);
		});
		empDto.setAddress(addresses);
		return empDto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Employee [employeeId=").append(employeeId).append(", firstName=").append(firstName)
				.append(", lastName=").append(lastName).append("]");
		return builder.toString();
	}
	
	
}

package com.infy.employeeManager.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class AddressDTO implements Serializable{
	private static final long serialVersionUID = 8284096519710049721L;
	private long addrId;
	@NotEmpty(message = "Please provide street")
	private String street;
	@NotEmpty(message = "Please provide city")
	private String city;
//	@Pattern(regexp="^[0-9]{6}$", message="Pincode must be of 6 digits")
	@Min(value = 100000, message = "Pincode should be 6 digits")
	@Max(value = 999999, message = "Pincode should be 6 digits")
	private long pincode;
	
	public AddressDTO() {
		super();
	}
	public AddressDTO(long addrId, String street, String city, long pincode) {
		super();
		this.addrId = addrId;
		this.street = street;
		this.city = city;
		this.pincode = pincode;
	}
	public long getAddrId() {
		return addrId;
	}
	public void setAddrId(long addrId) {
		this.addrId = addrId;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public long getPincode() {
		return pincode;
	}
	public void setPincode(long pincode) {
		this.pincode = pincode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddressDTO [addrId=").append(addrId).append(", street=").append(street).append(", city=")
				.append(city).append(", pincode=").append(pincode).append("]");
		return builder.toString();
	}
	
	
	
}

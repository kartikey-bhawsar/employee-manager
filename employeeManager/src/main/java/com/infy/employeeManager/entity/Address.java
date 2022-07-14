package com.infy.employeeManager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable{
	@Id
	@Column(name="addr_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
	@SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
	private long addrId;
	
	@Column(name="street")
	private String street;
	
	@Column(name="city")
	private String city;
	
	@Column(name="pincode")
	private long pincode;
	
	public Address() {
		
	}
	
	public Address(String street, String city, long pincode) {
		super();
		this.street = street;
		this.city = city;
		this.pincode = pincode;
	}

	public Address(long addrId, String street, String city, long pincode) {
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
		builder.append("Address [addrId=").append(addrId).append(", street=").append(street).append(", city=")
				.append(city).append(", pincode=").append(pincode).append("]");
		return builder.toString();
	}

	
	
}

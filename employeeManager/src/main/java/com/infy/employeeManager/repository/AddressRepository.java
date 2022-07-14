package com.infy.employeeManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.employeeManager.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{
	
}

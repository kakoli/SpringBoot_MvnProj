package com.example.springdata.emp.persistence;

import com.example.persistence.entity.Address;
import com.example.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}

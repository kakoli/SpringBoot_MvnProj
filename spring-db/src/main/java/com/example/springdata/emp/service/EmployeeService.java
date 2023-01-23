package com.example.springdata.emp.service;

import com.example.model.EmpRequest;
import com.example.persistence.entity.Employee;
import com.example.springdata.emp.exception.InputValidationException;

import java.util.Optional;

public interface EmployeeService {
	
	public Employee saveEmployee(EmpRequest req) throws InputValidationException;

	public Optional<Employee> getEmployee(Integer id);

	public Integer delEmployee(Integer id);

//	public ProductPriceEntsity modifyProductPrice(String id, ProductPriceEntity pojo);
//
//	public List<ProductPriceEntity> findAllByOrderByPriceAsc();
}

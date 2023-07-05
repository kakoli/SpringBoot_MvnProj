package com.example.springdata.emp.service;

import com.example.model.EmpData;
import com.example.model.EmpSimpleRequest;
import com.example.persistence.entity.EmployeeSimple;
import com.example.springdata.emp.exception.InputValidationException;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
	
	public EmployeeSimple saveEmployee(EmpSimpleRequest req) throws InputValidationException;

	public Optional<EmployeeSimple> getEmployee(Integer id);

	public Integer delEmployee(Integer id);

    public List<EmpData> getAllEmployees();

//	public ProductPriceEntsity modifyProductPrice(String id, ProductPriceEntity pojo);
//
//	public List<ProductPriceEntity> findAllByOrderByPriceAsc();
}

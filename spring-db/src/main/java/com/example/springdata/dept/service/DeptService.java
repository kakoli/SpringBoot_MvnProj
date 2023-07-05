package com.example.springdata.dept.service;

import com.example.persistence.entity.Department;
import com.example.springdata.dept.exception.InputValidationException;

import java.util.Optional;

public interface DeptService {
	
	public Department saveDepartment(Department req) throws InputValidationException;

	public Optional<Department> getDepartment(String name);
}

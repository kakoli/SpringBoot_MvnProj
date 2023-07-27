package com.example.springdata.dept.service;

import com.example.model.EmpData;
import com.example.persistence.entity.Department;
import com.example.persistence.entity.Employee;
import com.example.springdata.dept.exception.InputValidationException;

import java.util.List;
import java.util.Optional;

public interface DeptService {
	
	public Department saveDepartment(Department req) throws InputValidationException;

	public List<EmpData> getEmpsFromDept(String deptName);

	public Optional<Department> getDepartment(String name);

	public int updateDept(String hod, String deptName);
}

package com.example.springdata.dept.service;

import com.example.model.EmpData;
import com.example.persistence.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmpService {
    public Employee saveEmployee(Employee req) ;

    public Optional<Employee> getEmployee(Integer id);

    public List<EmpData> getAllEmps(String deptName);

    public int updateEmp(Integer sal, Integer empId);
}

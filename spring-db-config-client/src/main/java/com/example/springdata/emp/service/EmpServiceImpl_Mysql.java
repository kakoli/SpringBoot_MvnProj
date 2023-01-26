package com.example.springdata.emp.service;

import com.example.model.EmpRequest;
import com.example.persistence.entity.Employee;
import com.example.springdata.emp.exception.NotFoundException;
import com.example.springdata.emp.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class EmpServiceImpl_Mysql implements EmployeeService {
	
	@Autowired
	private EmployeeRepository empRepo;

	@Autowired
	private DataSource dataSource;

	public Employee saveEmployee(EmpRequest req) {
		Employee emp = Employee.builder()
				.name(req.getName())
				.deptm(req.getDept())
				.salary(req.getSalary()).build();
		Employee savedEntity = empRepo.save(emp);
		return savedEntity;
	}

	public Optional<Employee> getEmployee(Integer id) {
		Optional<Employee> emp = null;
		try {
			System.out.println("Driver name in getEmployee " +dataSource.getConnection().getMetaData().getDriverName());
			emp = empRepo.findById(id);
		}
		catch (SQLException ex) {

		}
		return  emp;
	}

	public Integer delEmployee(Integer id){
		if(!empRepo.existsById(id))
			throw new NotFoundException("Employee does not exist.");
		empRepo.deleteById(id);
		return id;
	}

}

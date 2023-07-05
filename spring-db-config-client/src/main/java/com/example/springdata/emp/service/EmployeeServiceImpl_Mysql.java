package com.example.springdata.emp.service;

import com.example.model.EmpSimpleRequest;
import com.example.persistence.entity.EmployeeSimple;
import com.example.springdata.emp.exception.NotFoundException;
import com.example.springdata.emp.persistence.EmployeeDao;
import com.example.springdata.emp.persistence.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeServiceImpl_Mysql implements EmployeeService {
	
	@Autowired
	private EmployeeRepository empRepo;

	@Autowired
	private EmployeeDao empDao;

	@Autowired
	private DataSource dataSource;

	public EmployeeSimple saveEmployee(EmpSimpleRequest req) {
		EmployeeSimple emp = EmployeeSimple.builder()
				.name(req.getName())
				.deptm(req.getDept())
				.salary(req.getSalary()).build();
		EmployeeSimple savedEntity = empRepo.save(emp);
		log.info("Saved emp with id " +savedEntity.getEmpId());
		return savedEntity;
	}

	public Optional<EmployeeSimple> getEmployee(Integer id) {
		Optional<EmployeeSimple> emp = null;
		emp = empRepo.findById(id);
		return  emp;
	}

	@Override
	public String getAllEmployees() throws SQLException {
		System.out.println("Datasource in Springboot " +dataSource.getClass().getName()); //com.zaxxer.hikari.HikariDataSource

		// Driver name - MySQL Connector/J
		System.out.println("Driver name in getAllEmployees " +dataSource.getConnection().getMetaData().getDriverName());
		return empDao.getEmps();
	}

	public Integer delEmployee(Integer id){
		if(!empRepo.existsById(id))
			throw new NotFoundException("EmployeeSimple does not exist.");
		empRepo.deleteById(id);
		return id;
	}

}

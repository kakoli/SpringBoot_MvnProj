package com.example.springdata.emp.service;

import com.example.model.EmpData;
import com.example.model.EmpRequest;
import com.example.persistence.entity.EmployeeSimple;
import com.example.springdata.emp.exception.NotFoundException;
import com.example.springdata.emp.persistence.EmployeeDao;
import com.example.springdata.emp.persistence.EmployeeSimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpServiceImpl_Mysql implements EmployeeService {
	
	@Autowired
	private EmployeeSimpleRepository empRepo;

	@Autowired
	private EmployeeDao empDao;

	public EmployeeSimple saveEmployee(EmpRequest req) {
		EmployeeSimple emp = EmployeeSimple.builder()
				.name(req.getName())
				.deptm(req.getDept())
				.salary(req.getSalary()).build();
		EmployeeSimple savedEntity = empRepo.save(emp);
		return savedEntity;
	}

	public Optional<EmployeeSimple> getEmployee(Integer id){
		Optional<EmployeeSimple> emp = empRepo.findById(id);
		return  emp;
	}

	public Integer delEmployee(Integer id){
		if(!empRepo.existsById(id))
			throw new NotFoundException("EmployeeSimple does not exist.");
		empRepo.deleteById(id);
		return id;
	}

	@Override
	public List<EmpData> getAllEmployees() {
		return empDao.getEmps();
	}

}

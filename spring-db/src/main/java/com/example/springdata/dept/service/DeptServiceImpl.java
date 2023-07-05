package com.example.springdata.dept.service;

import com.example.model.DeptRequest;
import com.example.persistence.entity.Department;
import com.example.springdata.dept.persistence.DeptRepository;
import com.example.springdata.dept.persistence.EmpDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DeptServiceImpl_Mysql implements DeptService {
	
	@Autowired
	private DeptRepository deptRepo;

	@Autowired
	private EmpDao empDao;

	public Department saveDepartment(DeptRequest req) {
		Department dept = Department.builder()
				.build();
		Department savedEntity = deptRepo.save(dept);
		log.info("Saved dept with id " +savedEntity.getDeptId());
		return savedEntity;
	}

	public Optional<Department> getDepartment(String s){
		Optional<Department> dept = null;
		dept = deptRepo.findDeptByName(s);
		return dept;
	}

}

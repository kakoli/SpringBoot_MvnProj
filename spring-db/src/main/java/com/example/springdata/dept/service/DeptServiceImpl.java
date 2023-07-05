package com.example.springdata.dept.service;

import com.example.persistence.entity.Department;
import com.example.springdata.dept.persistence.DeptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class DeptServiceImpl implements DeptService {
	
	@Autowired
	private DeptRepository deptRepo;

	@Transactional
	public Department saveDepartment(Department req) {
		Department savedEntity = deptRepo.save(req);
		log.info("Saved dept with id " +savedEntity.getEmps().size());
		return savedEntity;
	}

	public Optional<Department> getDepartment(String s){
		Optional<Department> dept = null;
		dept = deptRepo.findDeptByName(s);
		return dept;
	}

}

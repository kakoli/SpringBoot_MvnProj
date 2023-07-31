package com.example.springdata.dept.service;

import com.example.model.EmpData;
import com.example.persistence.entity.Department;
import com.example.springdata.dept.persistence.DeptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DeptServiceImpl implements DeptService {
	
	@Autowired
	private DeptRepository deptRepo;

	@Transactional
	@Override
	public Department saveDepartment(Department req) throws DataIntegrityViolationException {
		Department savedEntity = deptRepo.save(req);
		log.info("Saved dept with id " +savedEntity.getEmps().size());
		return savedEntity;
	}

	@Override
	public Optional<Department> getDepartment(String deptName){
		Optional<Department> dept = null;
		dept = deptRepo.findDeptByName(deptName);
		return dept;
	}

	@Override
	public List<EmpData> getEmpsFromDept(String deptName) {
		List<EmpData> data = null;
		data = deptRepo.getEmpsFromDept(deptName);
		log.info("Got emps " +data.size());
		return data;
	}

	@Override
	@Transactional
	public int updateDept(String hod, String deptName){
		return deptRepo.updateDept(hod, deptName);
	}

}

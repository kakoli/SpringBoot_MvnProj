package com.example.springdata.dept.service;

import com.example.model.EmpData;
import com.example.persistence.entity.Employee;
import com.example.springdata.dept.persistence.EmpDao;
import com.example.springdata.dept.persistence.EmpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpRepository empRepo;

    @Autowired
    private EmpDao empDao;

    @Transactional
    public Employee saveEmployee(Employee req) {
        log.info("In service: " + req.getJoindate());
        Employee savedEntity = null;
        savedEntity = empRepo.save(req);
        log.info(savedEntity.getEmpId() + " Saved emp with id " +savedEntity.getAddress().getCity());
        return savedEntity;
    }

    public Optional<Employee> getEmployee(Integer id){
        Optional<Employee> emp = null;
        emp = empRepo.findById(id);
        return emp;
    }

    @Override
    public List<EmpData> getAllEmps(String deptName) {
        List<EmpData> emps = null;
        emps = empDao.getAllEmps(deptName);
        log.info("Got emps " +emps.size());
        return emps;
    }

    @Transactional
    public int updateEmp(Integer sal, Integer empId) {
        int ret = empRepo.updateEmp(sal, empId);
        log.info("Updated rows " +ret);
        return ret;
    }
}

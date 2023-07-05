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
        /*Employee emp = Employee.builder()
                        .firstname(req.getFirstname())
                        .lastname(req.getLastname())
                        .salary(req.getSalary())
                        .joindate(req.getJoindate())
                        .address(req.getAddress())
                        .build();*/
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

    public List<EmpData> getAllEmps() {
        return empDao.getAllEmps();
    }
}

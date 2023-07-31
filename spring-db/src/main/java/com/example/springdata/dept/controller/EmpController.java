package com.example.springdata.dept.controller;

import com.example.model.Error;
import com.example.model.*;
import com.example.persistence.entity.Employee;
import com.example.springdata.dept.exception.InputValidationException;
import com.example.springdata.dept.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// For simplicity, have clubbed all methods in this controller
@RestController
@Slf4j
@RequestMapping("/api/v2/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    /* Save an employee with personal details like salary, address etc., but without dept.
    Will not work if emp.dept_id is not nullable. Can set default value in create table cmd.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee request) {
        ResponseEntity<Employee> ret = null;
        Employee entity = null;

        //log.info("In controller save emp request " + request);
        try {
            entity = empService.saveEmployee(request);
            ret = new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (InputValidationException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @GetMapping("/{empId}")
    public ResponseEntity<EmpGetResponse> getEmployee(@PathVariable("empId") Integer empId) {
        log.info("Start getEmployee : "+ empId);
        EmpGetResponse getResponse = null;
        ResponseEntity<EmpGetResponse> ret;
        Optional<Employee> emp = null;

        if(empId != null ) {
            emp = empService.getEmployee(empId);
            if (emp.isPresent()) {
                getResponse = EmpGetResponse.builder().emp(emp.get()).build();
                ret = new ResponseEntity<>(getResponse, HttpStatus.OK);
            }
            else {
                Error err = Error.builder()
                        .errorCode(HttpStatus.NOT_FOUND.value())
                        .errorDesc("Employee does not exist.").build();
                getResponse = EmpGetResponse.builder()
                        .errList(Collections.singletonList(err)).build();
                ret = new ResponseEntity<>(getResponse, HttpStatus.NOT_FOUND);
            }
        }
        else {
            log.error("Emp id is mandatory");
            Error err = Error.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .errorDesc("Employee id needed in request.").build();
            getResponse = EmpGetResponse.builder()
                    .errList(Collections.singletonList(err)).build();
            ret = new ResponseEntity<>(getResponse, HttpStatus.BAD_REQUEST);
        }
        return ret;
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeptEmpGetResponse> getAllEmps(@RequestParam(value="deptName", required=false) String  deptName) {
        log.info("Start getAllEmps : " + deptName);
        DeptEmpGetResponse getResponse;
        ResponseEntity<DeptEmpGetResponse> ret;
        List<EmpData> empList = null;

        //if(StringUtils.isBlank(deptName)) { // get emps of all depts
            long time = System.currentTimeMillis();
            empList = empService.getAllEmps(deptName);
            log.info("Time taken " + (System.currentTimeMillis() - time));
            getResponse = DeptEmpGetResponse.builder().emps(empList).build();
       // }

        //ret = ResponseEntity.ok(empList);
        ret = new ResponseEntity<>(getResponse, HttpStatus.OK);
        return ret;
    }

    @PutMapping("/{empId}")
    public ResponseEntity<EmpGetResponse> updateDepartment(@PathVariable("empId") Integer  empId,
                                                             @RequestBody EmpUpdateRequest request) {
        log.info("Start updateDepartment : "+ empId);
        ResponseEntity<EmpGetResponse> response;
        EmpGetResponse updResponse = null;
        try{
            int ret = empService.updateEmp(request.getSalary(), empId);
            if(ret > 0) {
                Employee empEntity = empService.getEmployee(empId).get();
                updResponse = EmpGetResponse.builder().emp(empEntity).build();
                response = new ResponseEntity<>(updResponse, HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        catch (RuntimeException e) {
            log.error("Input invalid ");
            e.printStackTrace();
            Error err = Error.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .errorDesc(e.getCause() != null ? e.getCause().getMessage() : e.getMessage()).build();
            updResponse = EmpGetResponse.builder()
                    .errList(Collections.singletonList(err)).build();
            response = new ResponseEntity<>(updResponse, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error("Unexpected Exception in updating department ");
            e.printStackTrace();
            Error err = Error.builder()
                    .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .errorDesc(e.getCause() != null ? e.getCause().getMessage() : e.getMessage()).build();
            updResponse = EmpGetResponse.builder()
                    .errList(Collections.singletonList(err)).build();
            response = new ResponseEntity<>(updResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}

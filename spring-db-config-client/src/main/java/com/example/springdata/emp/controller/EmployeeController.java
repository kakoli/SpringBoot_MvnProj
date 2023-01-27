package com.example.springdata.emp.controller;

import com.example.model.*;
import com.example.model.Error;
import com.example.persistence.entity.Employee;
import com.example.springdata.emp.exception.InputValidationException;
import com.example.springdata.emp.exception.NotFoundException;
import com.example.springdata.emp.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService empService;

    @PostMapping(path = "/emp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmpSaveResponse> saveEmployee(@RequestBody EmpRequest request) {

        EmpSaveResponse saveResponse = null;
        ResponseEntity<EmpSaveResponse> ret = null;
        Employee entity = null;

        System.out.println("In controller save " +request.getName());
        validate(request);
        try {
            entity = empService.saveEmployee(request);
            saveResponse = EmpSaveResponse.builder().emp(entity).build();
            ret = new ResponseEntity<>(saveResponse, HttpStatus.OK);
        }
        catch (RuntimeException e) {
            log.error("Input invalid ");
            e.printStackTrace();
            Error err = Error.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .errorDesc(e.getCause() != null ? e.getCause().getMessage() : e.getMessage()).build();
            saveResponse = EmpSaveResponse.builder()
                    .errList(Collections.singletonList(err))
                    .build();
            ret = new ResponseEntity<>(saveResponse, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error("Unexpected Exception in create user ");
            e.printStackTrace();
            Error err = Error.builder()
                    .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .errorDesc(e.getCause() != null ? e.getCause().getMessage() : e.getMessage()).build();
            saveResponse = EmpSaveResponse.builder()
                    .errList(Collections.singletonList(err))
                    .build();
            ret = new ResponseEntity<>(saveResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }

    @DeleteMapping("/emp/{empId}")
    public ResponseEntity<EmpDeleteResponse> deleteEmployee(@PathVariable("empId") Integer empId) {
        EmpDeleteResponse delResponse = null;
        Integer delId = null;
        ResponseEntity<EmpDeleteResponse> ret = null;

        if(empId == null) {
            log.error("Emp id is mandatory");
            Error err = Error.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .errorDesc("Employee id needed in request.").build();
            delResponse = EmpDeleteResponse.builder()
                    .errList(Collections.singletonList(err)).build();
            ret = new ResponseEntity<>(delResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            delId = empService.delEmployee(empId);
            delResponse = EmpDeleteResponse.builder()
                    .id(delId).build();
            ret = new ResponseEntity<>(delResponse, HttpStatus.OK);
        }
        catch (NotFoundException e) {
            log.error("Emp id not found");
            e.printStackTrace();
            Error err = Error.builder()
                    .errorCode(HttpStatus.NOT_FOUND.value())
                    .errorDesc(e.getCause() != null ? e.getCause().getMessage() : e.getMessage()).build();
            delResponse = EmpDeleteResponse.builder()
                    .errList(Collections.singletonList(err))
                    .build();
            ret = new ResponseEntity<>(delResponse, HttpStatus.NOT_FOUND);
        }
        return ret;
    }

    private void validate(EmpRequest request) throws InputValidationException {
        if(StringUtils.isBlank(request.getName()))
            throw new InputValidationException("Name cannot be blank.");
    }

    @GetMapping("/emp/{empId}")
    public ResponseEntity<EmpGetResponse> getEmployee(@PathVariable("empId") Integer empId) {
        log.info("Start getEmployee : "+ empId);
        EmpGetResponse getResponse = null;
        ResponseEntity<EmpGetResponse> ret;
        Optional<Employee> emp = null;

        if(empId != null ) {
            emp = empService.getEmployee(empId);
            if (!emp.isEmpty()) {
                getResponse = EmpGetResponse.builder()
                        .emp(emp.get()).build();
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

    @GetMapping("/emps")
    public String getAllEmployees() throws SQLException {
        log.info("Start getAllEmployees ");
        return empService.getAllEmployees();
    }
}

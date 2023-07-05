package com.example.springdata.dept.controller;

import com.example.model.*;
import com.example.model.Error;
import com.example.persistence.entity.Department;
import com.example.persistence.entity.Employee;
import com.example.springdata.dept.service.DeptService;
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
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/v2")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private EmpService empService;

    @PostMapping(path = "/emp", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(path = "/dept", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeptSaveResponse> saveDepartment(@RequestBody Department request) {
        log.info("Start  saveDepartment in controller " +request);
        DeptSaveResponse saveResponse = null;
        ResponseEntity<DeptSaveResponse> ret = null;
        Department entity = null;

        validate(request);
        try {
            entity = deptService.saveDepartment(request);
            saveResponse = DeptSaveResponse.builder().dept(entity).build();
            ret = new ResponseEntity<>(saveResponse, HttpStatus.OK);
        }
        catch (RuntimeException e) {
            log.error("Input invalid ");
            e.printStackTrace();
            Error err = Error.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .errorDesc(e.getCause() != null ? e.getCause().getMessage() : e.getMessage()).build();
            saveResponse = DeptSaveResponse.builder()
                    .errList(Collections.singletonList(err))
                    .build();
            ret = new ResponseEntity<>(saveResponse, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error("Unexpected Exception in saving department ");
            e.printStackTrace();
            Error err = Error.builder()
                    .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .errorDesc(e.getCause() != null ? e.getCause().getMessage() : e.getMessage()).build();
            saveResponse = DeptSaveResponse.builder()
                    .errList(Collections.singletonList(err))
                    .build();
            ret = new ResponseEntity<>(saveResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }

    private void validate(Department request) throws InputValidationException {
        if(StringUtils.isBlank(request.getName()))
            throw new InputValidationException("Name cannot be blank.");
    }
}

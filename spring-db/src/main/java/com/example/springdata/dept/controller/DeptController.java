package com.example.springdata.emp.controller;

import com.example.model.*;
import com.example.model.Error;
import com.example.persistence.entity.EmployeeSimple;
import com.example.springdata.emp.exception.InputValidationException;
import com.example.springdata.emp.service.EmployeeService;
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
public class EmployeeController {

    @Autowired
    private EmployeeService empService;

    @PostMapping(path = "/emp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmpSaveResponse> saveEmployee(@RequestBody EmpSimpleRequest request) {
        EmpSaveResponse saveResponse = null;
        ResponseEntity<EmpSaveResponse> ret = null;
        EmployeeSimple entity = null;

        log.info(log.getClass().getName());
        log.info("In controller save " +request.getName());
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

    private void validate(EmpSimpleRequest request) throws InputValidationException {
        if(StringUtils.isBlank(request.getName()))
            throw new InputValidationException("Name cannot be blank.");
    }

    @GetMapping("/emp/{empId}")
    public ResponseEntity<EmpGetResponse> getEmployee(@PathVariable("empId") Integer empId) throws InputValidationException {
        log.info("Start getEmployee : "+ empId);
        EmpGetResponse getResponse = null;
        ResponseEntity<EmpGetResponse> ret;
        Optional<EmployeeSimple> emp = null;

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
                        .errorDesc("EmployeeSimple does not exist.").build();
                getResponse = EmpGetResponse.builder()
                        .errList(Collections.singletonList(err)).build();
                ret = new ResponseEntity<>(getResponse, HttpStatus.NOT_FOUND);
            }
        }
        else {
            log.error("Emp id is mandatory");
            Error err = Error.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .errorDesc("EmployeeSimple id needed in request.").build();
            getResponse = EmpGetResponse.builder()
                    .errList(Collections.singletonList(err)).build();
            ret = new ResponseEntity<>(getResponse, HttpStatus.BAD_REQUEST);
        }
        return ret;
    }
}

package com.example.springdata.dept.controller;

import com.example.model.Error;
import com.example.model.*;
import com.example.persistence.entity.Department;
import com.example.springdata.dept.exception.InputValidationException;
import com.example.springdata.dept.service.DeptService;
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
@RequestMapping("/api/v2/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    // Save department with single/multiple employees
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeptSaveResponse> saveDepartment(@RequestBody Department request) {
        log.info("Start  saveDepartment in controller " +request);
        DeptSaveResponse saveResponse = null;
        ResponseEntity<DeptSaveResponse> ret = null;
        Department entity = null;

        try {
            validateDeptReq(request);
            entity = deptService.saveDepartment(request);
            saveResponse = DeptSaveResponse.builder().dept(entity).build();
            ret = new ResponseEntity<>(saveResponse, HttpStatus.OK);
        }
        catch (RuntimeException e) { // Name cannot be blank, Name cannot be duplicate
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

    private void validateDeptReq(Department request) throws InputValidationException {
        if(StringUtils.isBlank(request.getName()) || StringUtils.isEmpty(request.getName()))
            throw new InputValidationException("Department name cannot be blank.");
    }

    @GetMapping("/{deptName}")
    public ResponseEntity<Department> getDepartment(@PathVariable("deptName") String  deptName) {
        log.info("Start getDepartment : "+ deptName);
        ResponseEntity<Department> ret;
        Optional<Department> dept = null;

        dept = deptService.getDepartment(deptName);
        if(dept.isPresent())
            ret = new ResponseEntity<>(dept.get(), HttpStatus.OK);
        else
            ret = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return ret;
    }

    // Returns Employees with address and department.
    @GetMapping("/{deptName}/emps")
    public ResponseEntity<DeptEmpGetResponse> getAllEmpsFromDept(@PathVariable("deptName") String  deptName) {
        log.info("Start getAllDeptEmps :");
        DeptEmpGetResponse getResponse;
        ResponseEntity<DeptEmpGetResponse> ret;
        List<EmpData> empList = null;

        if(!StringUtils.isBlank(deptName)) {
            try {
                empList = deptService.getEmpsFromDept(deptName);
                getResponse = DeptEmpGetResponse.builder().emps(empList).build();

                //ret = ResponseEntity.ok(empList);
                ret = new ResponseEntity<>(getResponse, HttpStatus.OK);
            }catch(Exception e){
                log.error("Unexpected Exception in getting all emps of department ");
                e.printStackTrace();
                Error err = Error.builder()
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errorDesc(e.getCause() != null ? e.getCause().getMessage() : e.getMessage()).build();
                getResponse = DeptEmpGetResponse.builder()
                        .errList(Collections.singletonList(err))
                        .build();
                ret = new ResponseEntity<>(getResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            //ret = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            System.out.println("In here");
            ret = ResponseEntity.badRequest().build(); //400, noContent is 204
        }
        return ret;
    }

    /*@PatchMapping can be used to apply the partial update to a resource having many attributes.
    But here we are using PutMapping.
    public boolean partialUpdate(int id, String key, String value)
     */
    @PutMapping("/{deptName}")
    public ResponseEntity<DeptSaveResponse> updateDepartment(@PathVariable("deptName") String  deptName,
                                              @RequestBody DeptUpdateRequest request) {
        log.info("Start updateDepartment : "+ deptName);
        ResponseEntity<DeptSaveResponse> response;
        DeptSaveResponse updResponse = null;
       try{
            int ret = deptService.updateDept(request.getHod(), deptName);
            if(ret > 0) {
                Department deptEntity = deptService.getDepartment(deptName).get();
                updResponse = DeptSaveResponse.builder().dept(deptEntity).build();
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
            updResponse = DeptSaveResponse.builder()
                    .errList(Collections.singletonList(err)).build();
            response = new ResponseEntity<>(updResponse, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error("Unexpected Exception in updating department ");
            e.printStackTrace();
            Error err = Error.builder()
                    .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .errorDesc(e.getCause() != null ? e.getCause().getMessage() : e.getMessage()).build();
            updResponse = DeptSaveResponse.builder()
                    .errList(Collections.singletonList(err)).build();
            response = new ResponseEntity<>(updResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}

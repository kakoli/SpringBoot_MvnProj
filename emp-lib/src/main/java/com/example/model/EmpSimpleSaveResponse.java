package com.example.model;

import com.example.persistence.entity.EmployeeSimple;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpSaveResponse extends EmpResponse {

    // By default, json response has node name as 'emp_simple'
    @JsonProperty("emp")
    private EmployeeSimple emp_simple;

    @Builder
    public EmpSaveResponse(List<Error> errList, EmployeeSimple emp) {
        super(errList);
        this.emp_simple = emp;
    }
}

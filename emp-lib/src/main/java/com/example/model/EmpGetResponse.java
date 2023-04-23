package com.example.model;

import com.example.persistence.entity.EmployeeSimple;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmpGetResponse extends EmpResponse {
    private EmployeeSimple emp;

    @Builder
    public EmpGetResponse(List<Error> errList, EmployeeSimple emp) {
        super(errList);
        this.emp = emp;
    }
}

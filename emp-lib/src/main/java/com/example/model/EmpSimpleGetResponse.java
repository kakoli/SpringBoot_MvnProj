package com.example.model;

import com.example.persistence.entity.EmployeeSimple;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmpSimpleGetResponse extends BaseResponse {
    private EmployeeSimple emp;

    @Builder
    public EmpSimpleGetResponse(List<Error> errList, EmployeeSimple emp) {
        super(errList);
        this.emp = emp;
    }
}

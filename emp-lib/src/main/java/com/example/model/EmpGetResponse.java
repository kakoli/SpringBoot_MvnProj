package com.example.model;

import com.example.persistence.entity.Department;
import com.example.persistence.entity.Employee;
import com.example.persistence.entity.EmployeeSimple;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmpGetResponse extends BaseResponse {
    private Employee emp;

    @Builder
    public EmpGetResponse(List<Error> errList, Employee emp) {
        super(errList);
        this.emp = emp;
    }
}

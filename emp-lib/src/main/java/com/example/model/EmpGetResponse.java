package com.example.model;

import com.example.persistence.entity.Employee;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmpGetResponse extends EmpResponse {
    private Employee emp;

    @Builder
    public EmpGetResponse(List<Error> errList, Employee emp) {
        super(errList);
        this.emp = emp;
    }
}

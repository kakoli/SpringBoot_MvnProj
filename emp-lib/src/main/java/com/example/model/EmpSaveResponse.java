package com.example.model;

import com.example.persistence.entity.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpSaveResponse extends EmpResponse {
    private Employee emp;

    @Builder
    public EmpSaveResponse(List<Error> errList, Employee emp) {
        super(errList);
        this.emp = emp;
    }
}

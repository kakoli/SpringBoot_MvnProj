package com.example.model;

import com.example.persistence.entity.Employee;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DeptEmpGetResponse extends BaseResponse {
    private List<EmpData> emps;

    @Builder
    public DeptEmpGetResponse(List<Error> errList, List<EmpData> emps) {
        super(errList);
        this.emps = emps;
    }
}

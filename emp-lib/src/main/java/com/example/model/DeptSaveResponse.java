package com.example.model;

import com.example.persistence.entity.Department;
import com.example.persistence.entity.EmployeeSimple;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeptSaveResponse extends BaseResponse {

    // By default, json response has node name as 'dept'
    private Department dept;

    @Builder
    public DeptSaveResponse(List<Error> errList, Department dept) {
        super(errList);
        this.dept = dept;
    }
}

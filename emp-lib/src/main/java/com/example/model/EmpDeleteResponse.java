package com.example.model;

import com.example.persistence.entity.Employee;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmpDeleteResponse extends EmpResponse {
    private Integer deletedId;

    @Builder
    public EmpDeleteResponse(List<Error> errList, Integer id) {
        super(errList);
        this.deletedId = id;
    }
}

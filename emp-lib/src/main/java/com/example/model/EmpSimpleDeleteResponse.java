package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmpSimpleDeleteResponse extends BaseResponse {
    private Integer deletedId;

    @Builder
    public EmpSimpleDeleteResponse(List<Error> errList, Integer id) {
        super(errList);
        this.deletedId = id;
    }
}

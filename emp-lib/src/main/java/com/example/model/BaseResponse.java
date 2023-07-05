package com.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // If no errors, the tag will be removed from json output
public abstract class BaseResponse {
    private final List<Error> errors;
}

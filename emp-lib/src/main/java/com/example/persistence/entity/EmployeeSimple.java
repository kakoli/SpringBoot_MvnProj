package com.example.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="EMP_SIMPLE")
public class EmployeeSimple {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    @JsonIgnore //Not included in input/output json
    private Integer empId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("salary")
    private Integer salary;

    @Column(name="dept")
    @JsonProperty("department")
    private String deptm; //should be db col name if @Column not present

    /*public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }*/
}

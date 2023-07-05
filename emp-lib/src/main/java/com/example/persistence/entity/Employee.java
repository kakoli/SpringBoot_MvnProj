package com.example.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="EMP")
@JsonPropertyOrder({"firstname", "lastname", "salary", "joindate", "address"})
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    @JsonIgnore
    private Integer empId;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("salary")
    private Integer salary;

    @JsonProperty("joindate")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = JsonFormat.DEFAULT_TIMEZONE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date joindate; //should be db col name if @Column not present

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="addr_id", referencedColumnName = "id")
    private Address address;

    /*@ManyToOne
    @JoinColumn(name="dept_id")
    @JsonIgnore
    private Department dept;*/

    /*public void setDept(Department d){
        this.dept = d;
    }

    public Department getDept() {
        return dept;
    }*/

    @Override
    public boolean equals(Object o) {
        boolean ret = false;
        if(this == o)
            ret = true;
        if(o instanceof Employee && empId != null) {
            if(empId.equals(((Employee) o).getEmpId()))
                ret = true;
        }
        return ret;
    }

    @Override
    public int hashCode(){
        return this.getClass().hashCode();
    }
}

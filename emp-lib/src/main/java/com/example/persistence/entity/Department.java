package com.example.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="DEPT")
public class Department {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    @JsonIgnore
    private Integer deptId;

    @Column(name="name")
    private String name;

    @Column(name="hod")
    private String hod;

    @Column(name="size")
    private Integer size;

    /* One To Many Unidirectional mapping
       If we omit JoinColumn, then there will be 3 tables instead of 2.
     */
    @OneToMany( cascade = CascadeType.ALL, fetch= FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="dept_id")
    List<Employee> emps = new ArrayList<>();

    /*public void addEmp(Employee e) {
        emps.add(e);
        e.setDept(this);

    }
    public void removeEmp(Employee e) {
        emps.remove(e);
        e.setDept(null);
    }*/
}

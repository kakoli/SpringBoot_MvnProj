package com.example.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name="zip")
    private Integer zip;

}

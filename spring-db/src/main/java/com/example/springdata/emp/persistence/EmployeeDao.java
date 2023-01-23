package com.example.springdata.emp.persistence;


import com.example.model.EmpData;

import java.util.List;

public interface EmployeeDao {

    public List<EmpData> getEmps();
}

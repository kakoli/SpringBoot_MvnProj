package com.example.springdata.emp.persistence;


import com.example.model.EmpData;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDao {

    public String getEmps() throws SQLException;
}

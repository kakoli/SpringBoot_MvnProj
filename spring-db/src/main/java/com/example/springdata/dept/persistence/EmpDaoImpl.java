package com.example.springdata.dept.persistence;

import com.example.model.EmpData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EmpDaoImpl implements EmpDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EmpDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    /* EmpData is the model for the fields of select query which is coming from multiple tables other than 'emp'.
     Since this uses JdbcTemplate, this method is part of Dao and not EmployeeRepository, where db operations
     are built-in or as parameter to @Query annotation.
    */
    public  List<EmpData> getAllEmps(String deptName) {
        String query = "SELECT e.firstname, e.lastname, d.name, a.city, a.state, a.zip FROM " +
                    "emp e, dept d, address a WHERE d.id = e.dept_id and a.id = e.addr_id and d.name = ?";
        System.out.println("Before jdbcTemplate.query ");
        List<EmpData> emps = jdbcTemplate.query(query, new EmpMapper(), deptName);
        return emps;
    }
}

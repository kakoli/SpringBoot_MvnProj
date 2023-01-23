package com.example.springdata.emp.persistence;

import com.example.model.EmpData;
import com.example.persistence.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EmployeeDaoImpl implements EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    /* AttributeData is the model for the fields of select query which is coming from
    multiple tables other than 'user'. Hence this is part of Dao and jdbcTemplate
    and not EmployeeRepository, where db operations are built-in or as parameter to
    @Query annotation.
    */
    public  List<EmpData> getEmps() {
        String query = "SELECT e.id, e.firstname, e.lastname, d.name FROM emp e, dept d, project p," +
                        " empproject ep WHERE e.deptid = d.id AND e.id = ep.empid and" +
                        " p.firstname = ?1";
        //List<EmpData> empData = jdbcTemplate.query(query, new EmpMapper());
        //, new Object[]{loginName, productTypeId});
        return null;
    }
}

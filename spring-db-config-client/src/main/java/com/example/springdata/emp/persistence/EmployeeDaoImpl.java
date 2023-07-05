package com.example.springdata.emp.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
@Slf4j
public class EmployeeDaoImpl implements EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

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
    public  String getEmps() throws SQLException {
        return "Success";
    }
}

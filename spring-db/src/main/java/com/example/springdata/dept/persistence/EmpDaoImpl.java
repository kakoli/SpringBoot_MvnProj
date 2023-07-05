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
    /* AttributeData is the model for the fields of select query which is coming from
    multiple tables other than 'emp'. Hence this is part of Dao and jdbcTemplate
    and not EmployeeSimpleRepository, where db operations are built-in or as parameter to
    @Query annotation.
    */
    public  List<EmpData> getAllEmps() {
        String query = "SELECT e.firstname, e.lastname, e.joindate FROM emp e, address a WHERE \n" +
                        "e.addr_id = a.id";
        //List<EmpData> empData = jdbcTemplate.query(query, new EmpMapper());
        //, new Object[]{loginName, productTypeId});
        return null;
    }
}

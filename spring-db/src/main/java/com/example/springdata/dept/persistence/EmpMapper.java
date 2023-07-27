package com.example.springdata.dept.persistence;

import com.example.model.EmpData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpMapper implements RowMapper<EmpData> {

    @Override
    public EmpData mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmpData empData = EmpData.builder().build();
        empData.setFirstname(rs.getString(""));

        empData.setLastname(rs.getString("firstName"));
        //empData.setDeptname(rs.getString("attributename"));

        return empData;
    }
}

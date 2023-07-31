package com.example.springdata.dept.persistence;

import com.example.model.EmpData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpMapper implements RowMapper<EmpData> {

    @Override
    public EmpData mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmpData empData = EmpData.builder()
                        .firstname(rs.getString("firstname"))
                        .lastname(rs.getString("lastname"))
                        .deptname(rs.getString("name"))
                        .city(rs.getString("city"))
                        .state(rs.getString("state"))
                        .zip(rs.getInt("zip")).build();

        return empData;
    }
}

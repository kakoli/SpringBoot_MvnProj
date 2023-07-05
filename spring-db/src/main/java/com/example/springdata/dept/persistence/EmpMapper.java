package com.example.springdata.dept.persistence;

import com.example.model.EmpData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpMapper implements RowMapper<EmpData> {

    @Override
    public EmpData mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmpData empData = new EmpData();
        /*empData.setAttributeId(rs.getLong("attributeid"));
        empData.setAttributeName(rs.getString("attributename"));
        empData.setAttributeTypeId(rs.getLong("attributetypeid"));*/

        return empData;
    }
}

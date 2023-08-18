package com.example.springdata.emp.controller;

import com.example.model.EmpSimpleRequest;
import com.example.model.BaseResponse;
import com.example.model.EmpSimpleSaveResponse;
import com.example.persistence.entity.EmployeeSimple;
import com.example.springdata.emp.controller.EmployeeController;
import com.example.springdata.emp.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @MockBean
    EmployeeService mockService;

    @MockBean
    BaseResponse mockResponse;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testSaveEmp() throws Exception {
        String name = "TEST";
        String dept = "test_dept";
        int sal = 3333;
        int id = 123;
        EmpSimpleRequest testReq = EmpSimpleRequest.builder().name(name).dept(dept).salary(sal).build();
        EmployeeSimple empEntity = EmployeeSimple.builder().empId(id).name(name).deptm(dept).salary(sal).build();
        EmpSimpleSaveResponse response = EmpSimpleSaveResponse.builder().emp(empEntity).errList(null).build();

        // Mockito calls
        Mockito.when(mockService.saveEmployee(testReq)).thenReturn(empEntity);

        //$” is the response JSON root level.
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/emp")
                        .content(new ObjectMapper().writeValueAsString(testReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.emp.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emp.department").value(dept));

    }
}

package com.example.springdata.emp.controller;

import com.example.model.EmpRequest;
import com.example.persistence.entity.Employee;
import com.example.springdata.emp.service.EmployeeService;
import com.example.springdata.emp.util.HashUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService empService;

    @MockBean
    HashUtil hashUtil;

    private static final Long MOCK_ID = 321L;

    /*@BeforeEach
    void setUp() {
        response = CourseResponse.builder().status("success").courseDataList(Collections.singletonList(CourseData
                .builder().courseDescription("This is the test course cobalt team")
                .courseStartDate(new Date())
                .courseEndDate(new Date()).courseTitle("Course Title Test")
                .instructorLoginName("loginname")
                .build())).build();
    }*/

    @Test
    @DisplayName("Create User - Valid")
    void createCustomUserValid() throws Exception {
        EmpRequest req = EmpRequest.builder()
                            .name("Tim")
                            .dept("Sales")
                            .salary(5000)
                            .build();
        Employee expEmp = Employee.builder()
                            .name("Tim")
                            .deptm("Sales")
                            .salary(5000).build();
        String reqStr = null;
        reqStr = new ObjectMapper().writeValueAsString(req);

        Mockito.when(empService.saveEmployee(req)).thenReturn(expEmp);
        //Mockito.when(empService.createUser(req)).thenReturn(Long.valueOf("123"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1")
                        .content(reqStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.smsUserId").value(123L));
    }

    @Test
    @DisplayName("Get User - Valid")
    void getCustomUserValid() throws Exception {
        /*String externalCourseId = "uzumaki123";
        Mockito.when(courseService.getCourse(externalCourseId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/course/custom/" + externalCourseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseDataList[0].externCourseId")
                        .value("goku123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseDataList[0].instructorLoginName").
                        value("loginname"));*/
    }
}

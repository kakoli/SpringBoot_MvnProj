package com.example.springdata.emp.controller;

import com.example.springdata.emp.model.UserRequest;
import com.example.springdata.emp.service.UserService;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

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
        UserRequest req = UserRequest.builder()
                            .defaultRole("Student")
                            .firstName("Tiensan")
                            .lastName("Chung")
                            .loginName("tst_0")
                            .build();
        String reqStr = null;
        reqStr = new ObjectMapper().writeValueAsString(req);

        //Mockito.when(courseService.getCourseID(externalCourseId)).thenReturn(Long.valueOf("123"));
        Mockito.when(userService.createUser(req)).thenReturn(Long.valueOf("123"));

        mockMvc.perform(MockMvcRequestBuilders.post("/smsuser/custom")
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

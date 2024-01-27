package com.example.mprprojectmvn;

import com.example.mprprojectmvn.course.data.Course;
import com.example.mprprojectmvn.course.data.CourseRepository;
import com.example.mprprojectmvn.student.data.Student;
import com.example.mprprojectmvn.student.data.StudentRepository;
import com.example.mprprojectmvn.student.data.StudentUnit;
import com.example.mprprojectmvn.student.resource.CreateStudent;
import com.example.mprprojectmvn.student.service.StudentService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentResourceRestAssuredTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudentRepository repository;
    @Autowired
    private CourseRepository courseRepository;
    @LocalServerPort
    private int port;
    @BeforeEach
    void setUp(){
        mockMvc(mockMvc);
    }
    @AfterEach
    void cleanUp(){
        repository.deleteAll();
    }
    @Test
    void givenStudentInDbWhenGetByIdThenReturnStudentDto(){
        var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
        courseRepository.save(course1);
        var student = repository.save(new Student("M", "C",course1, StudentUnit.GDANSK,15L));
        given().
                port(port).
        when().get("/students/" + student.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(student.getId().toString()))
                .body("name",equalTo(student.getName()))
                .body("surname",equalTo(student.getSurname()))
                .body("courseName",equalTo(student.getCourse().getCourseName()))
                .body("unit",equalTo(student.getUnit().toString()))
                .body("index",equalTo(student.getIndex().intValue()));
    }


    @Test
    void givenStudentDataWhenCreateStudentThenRespondIsCreated(){
        var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
        courseRepository.save(course1);
        given().contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .body(new CreateStudent("Karola", "P", "course1", StudentUnit.GDANSK))
                .port(port)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void givenStudentsInDbWhenGetByNameThenReturnList(){
        var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
        courseRepository.save(course1);
        var student = repository.save(new Student("M", "C", course1, StudentUnit.GDANSK,15L));
        given()
                .param("name","M")
                .port(port)
                .when().get("/students").then()
                .body("$.size()",equalTo(1))
                .body("[0].id",equalTo(student.getId().toString()))
                .body("[0].name",equalTo(student.getName()))
                .body("[0].unit",equalTo(student.getUnit().toString()))
                .body("[0].index",equalTo(student.getIndex().intValue()));
    }
}

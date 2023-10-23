package com.example.mprprojectmvn.service;

import com.example.mprprojectmvn.data.Student;
import com.example.mprprojectmvn.data.StudentRepository;
import com.example.mprprojectmvn.data.StudentUnit;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@Log
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Spy
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

//    @BeforeAll
//    static void setUpAll(){
//        log.info("Before all tests this setup is called");
//    }
//    @BeforeEach
//    void setUp(){
//        log.info("Before each test this setup is called");
//    }
//
//    @AfterEach
//    void cleanUp(){
//        log.info("After each test this cleanup is called");
//    }
//
//    @AfterAll
//    static void cleanUpAll(){
//        log.info("After all tests this cleanup is called");
//    }
    @Test
    void givenGdanskUnitWhenSaveStudentThenGetValidIndex(){
        //given
        var student = new Student(UUID.fromString("6f16f034-de79-47ad-b05f-77ec29b7a453"),"Magdalena", StudentUnit.GDANSK, null);
//        maxIndex = 6L;
//        when(studentRepository.getMaxIndex()).thenReturn(5L); // wywoła podaną metodę dla mocka "studentRepository" (bo sam mock nie zwraca nic gdy są wywoływane metody)


        //when
       var savedStudent = studentService.saveStudent(student);

        //then
        assertEquals(student.id(),savedStudent.id());
        assertEquals(student.name(),savedStudent.name());
        assertEquals(student.unit(),savedStudent.unit());
        assertEquals(15,savedStudent.index());
        verify(studentRepository,times(1)).saveStudent(any());
    }

//    @Test
//    void givenWarszawaUnitWhenSaveStudentThenGetValidIndex(){
//        //given
//        var student = new Student(UUID.fromString("6f16f034-de79-47ad-b05f-77ec29b7a453"),"Magdalena", StudentUnit.WARSZAWA, null);
//        //when
//        var savedStudent = studentService.saveStudent(student);
//
//        //then
//        assertEquals(student.id(),savedStudent.id());
//        assertEquals(student.name(),savedStudent.name());
//        assertEquals(student.unit(),savedStudent.unit());
//        assertEquals(50,savedStudent.index());
//        verify(studentRepository,times(1)).saveStudent(any());
//    }
}

package com.example.mprprojectmvn.service;

import com.example.mprprojectmvn.course.data.Course;
import com.example.mprprojectmvn.course.data.CourseRepository;
import com.example.mprprojectmvn.course.resource.CourseMapper;
import com.example.mprprojectmvn.course.service.CourseService;
import com.example.mprprojectmvn.student.data.Student;
import com.example.mprprojectmvn.student.data.StudentRepository;
import com.example.mprprojectmvn.student.data.StudentUnit;
import com.example.mprprojectmvn.student.exceptionhandler.RecordNotFoundException;
import com.example.mprprojectmvn.student.resource.StudentDto;
import com.example.mprprojectmvn.student.resource.StudentMapper;
import com.example.mprprojectmvn.student.service.StudentService;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Log
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentServiceTest {

    private StudentRepository studentRepository = mock(StudentRepository.class);
    private CourseRepository courseRepository = mock(CourseRepository.class);
    private CourseMapper courseMapper = mock(CourseMapper.class);
    private CourseService courseService =mock(CourseService.class);
    private StudentMapper studentMapper = mock(StudentMapper.class);
//    @Autowired
//    private CourseRepository courseRepository;

  //  private StudentMapper studentMapper = new StudentMapper(courseRepository);

    private StudentService studentService = new StudentService(studentRepository, courseRepository, courseService,studentMapper,courseMapper);

    @BeforeEach
    void setUp(){
    }

    @BeforeAll
    static void setUpAll(){
        log.info("Before all tests this setup is called");
    }
//    @BeforeEach
//    void setUp(){
//        log.info("Before each test this setup is called");
//    }

    @AfterEach
    void cleanUp(){
        log.info("After each test this cleanup is called");
    }

    @AfterAll
    static void cleanUpAll(){
        log.info("After all tests this cleanup is called");
    }
    @Test
    void givenGdanskUnitWhenSaveStudentThenGetValidIndex(){
        //given
 //       var student = new CreateStudent("Magdalena", "C",StudyCourseType.COMPUTER_SCIENCE,StudentUnit.GDANSK);
//        maxIndex = 6L;
//        when(studentRepository.getMaxIndex()).thenReturn(5L); // wywoła podaną metodę dla mocka "studentRepository" (bo sam mock nie zwraca nic gdy są wywoływane metody)


//        //when
//       var savedStudent = studentService.saveStudent(student);
//
//        //then
//        assertEquals(student.getName(),savedStudent.getName());
//        assertEquals(student.getUnit(),savedStudent.getUnit());
//        verify(studentRepository,times(1)).saveStudent(any());
    }

//    @Test
//    void givenWarszawaUnitWhenSaveStudentThenGetValidIndex(){
//        //given
//        var student = new CreateStudent("Magdalena", StudentUnit.WARSZAWA);
//        //when
//        var savedStudent = studentService.saveStudent(student);
//
//        //then
//        assertEquals(student.name(),savedStudent.name());
//        assertEquals(student.unit(),savedStudent.unit());
//        verify(studentRepository,times(1)).saveStudent(any());
//    }

    @Test
    void givenExistingSurnameWhenGetStudentsBySurnameThenReturnListWithStudents(){
        var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
        courseRepository.save(course1);
        Student student1 = new Student(UUID.randomUUID(), "M", "Kowal", course1, StudentUnit.GDANSK, 0L);
        Student student2 = new Student(UUID.randomUUID(), "S", "Ziel", course1, StudentUnit.GDANSK, 5L);
        Student student3 = new Student(UUID.randomUUID(), "W", "Kowal", course1, StudentUnit.GDANSK, 10L);
        List<Student> predestinedMatches = Arrays.asList(student1, student3);

        when(studentRepository.getStudentsBySurname("Kowal")).thenReturn(predestinedMatches);

        List<StudentDto> foundStudents = studentService.getStudentsBySurname("Kowal");

        assertEquals(predestinedMatches.stream().map(studentMapper::toDto).toList(),foundStudents);
    }

    @Test
    void givenNonExistingSurnameWhenGetStudentsBySurnameThenReturnEmptyList(){
        var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
        courseRepository.save(course1);
        Student student1 = new Student(UUID.randomUUID(), "M", "Kowal", course1, StudentUnit.GDANSK, 0L);
        Student student2 = new Student(UUID.randomUUID(), "S", "Ziel", course1, StudentUnit.GDANSK, 5L);
        Student student3 = new Student(UUID.randomUUID(), "W", "Kowal", course1, StudentUnit.GDANSK, 10L);
        List<Student> predestinedMatches = new ArrayList<>();

        when(studentRepository.getStudentsBySurname("Nowak")).thenReturn(predestinedMatches);

        List<StudentDto> foundStudents = studentService.getStudentsBySurname("Nowak");

        assertEquals(predestinedMatches.stream().map(studentMapper::toDto).toList(),foundStudents);
    }

    @Test
    void givenExistingCourseWhenGetStudentsCourseThenReturnListWithStudents(){
        var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
        var course2 = new Course(2,"course2",0,new ArrayList<>(),"BBB");
        courseRepository.save(course1);
        courseRepository.save(course2);
        Student student1 = new Student(UUID.randomUUID(), "M", "Kowal", course1, StudentUnit.GDANSK, 0L);
        Student student2 = new Student(UUID.randomUUID(), "S", "Ziel", course2, StudentUnit.GDANSK, 5L);
        List<Student> predestinedMatches = Arrays.asList(student2);

        when(studentRepository.getStudentsByCourseName(course1.getCourseName())).thenReturn(predestinedMatches);

        List<StudentDto> foundStudents = studentService.getStudentsByCourseName(course1.getCourseName());

        assertEquals(predestinedMatches.stream().map(studentMapper::toDto).toList(),foundStudents);
    }
//    @Test
//    void givenExistingStudentIdAndUpdateDtoWhenUpdateStudentByIdThenUpdateAndReturnStudent() {
//        Student existingStudent = new Student(UUID.randomUUID(), "M", "K", StudyCourseType.NEW_MEDIA_ART, StudentUnit.GDANSK, 0L);
//        StudentDto updatedStudentDto = new StudentDto(existingStudent.getId(),"Magdalena","C", StudyCourseType.COMPUTER_SCIENCE,StudentUnit.GDANSK, 5L);
//
//        when(studentRepository.findById(eq(existingStudent.getId()))).thenReturn(Optional.of(existingStudent));
//        studentMapper.studentDtoToEntity(updatedStudentDto);
//        when(studentRepository.save(eq(existingStudent))).thenReturn(existingStudent);
//
//        studentService.updateStudentById(updatedStudentDto, existingStudent.getId());
//
//        assertEquals(existingStudent, updatedStudent);
//        verify(studentRepository, times(1)).findById(eq(existingStudent.getId()));
//        verify(studentRepository, times(1)).save(eq(existingStudent));
//    }

    //?
    @Test
    void givenNonExistingStudentIdWhenUpdateStudentByIdThenThrowRecordNotFoundException() {
        var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
        courseRepository.save(course1);
        StudentDto updatedStudentDto = new StudentDto(UUID.randomUUID(),"Magdalena","C", course1.getCourseName(),StudentUnit.GDANSK, 5L);

        when(studentRepository.findById(eq(updatedStudentDto.id()))).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            studentService.updateStudentById(updatedStudentDto,updatedStudentDto.id());
        });

        assertEquals("There's no such student in the database", exception.getMessage());
        verify(studentRepository, times(1)).findById(any());
        verify(studentRepository, never()).save(any());
    }

}

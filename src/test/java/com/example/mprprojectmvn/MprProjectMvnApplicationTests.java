package com.example.mprprojectmvn;

import com.example.mprprojectmvn.course.data.Course;
import com.example.mprprojectmvn.course.data.CourseRepository;
import com.example.mprprojectmvn.student.data.Student;
import com.example.mprprojectmvn.student.data.StudentRepository;
import com.example.mprprojectmvn.student.data.StudentUnit;
import com.example.mprprojectmvn.student.exceptionhandler.RecordNotFoundException;
import com.example.mprprojectmvn.student.resource.StudentDto;
import com.example.mprprojectmvn.student.resource.StudentResource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MprProjectMvnApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private StudentResource studentResource;

	@Autowired
	private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;
	@Test
	void contextLoads() {
		assertThrows(RecordNotFoundException.class, () -> studentResource.getStudentById(UUID.randomUUID()));
	}

	@Test
	void givenNoStudents_whenGetById_thenRespondWithNotFound() throws Exception{
		var response = mockMvc.perform(get("/students/ad63cd7a-59bf-4a28-9d1f-6a8a4ebd2328")).andDo(print()).andReturn().getResponse();
		assertEquals(response.getStatus(), 404);
		assertTrue(response.getContentAsString().contains("not found"));
	}


	@SneakyThrows
	@Test
  void givenStudentsWithDifferentName_whenGetByName_ThenReturnStudentWithGivenName() throws Exception {
        var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
        courseRepository.save(course1);
		var student1 = new Student(UUID.randomUUID(),"Magdalena","C", course1, StudentUnit.GDANSK,11L);
		var student2 = new Student(UUID.randomUUID(),"Aga", "A",course1, StudentUnit.GDANSK,12L);
		studentRepository.saveAll(List.of(student1,student2));

		mockMvc.perform(get("/students/name/Magdalena"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"name\":\"Magdalena\"")))
				.andExpect(content().string(not(containsString("\"name\":\"Aga\""))));
	}

	@SneakyThrows
	@Test
	void givenStudentsWithDifferentName_whenGetByName_ThenReturnStudentWithGivenNameFromGDANSK() throws Exception {
        var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
        courseRepository.save(course1);
        var student1 = new Student(UUID.randomUUID(),"Magdalena","C", course1, StudentUnit.GDANSK,11L);
        var student2 = new Student(UUID.randomUUID(),"Aga", "A",course1, StudentUnit.GDANSK,12L);
        var student3 = new Student(UUID.randomUUID(),"Aga", "A",course1, StudentUnit.WARSZAWA,12L);
		studentRepository.saveAll(List.of(student1,student2, student3));

		var response = mockMvc.perform(get("/students/name/Magdalena"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();
		var studentList = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<StudentDto>>() {});
		assertEquals(1,studentList.size());
		var returnedStudent = studentList.get(0);
		assertEquals(student1.getId(), returnedStudent.id());
	}
	@SneakyThrows
	@Test
	void givenStudentsWithDifferentName_whenDeleteByName_ThenDeleteStudentWithGivenName() throws Exception {
        var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
        courseRepository.save(course1);
		var student1 = new Student(UUID.randomUUID(),"Magdalena","C", course1, StudentUnit.GDANSK,11L);
		var student2 = new Student(UUID.randomUUID(),"Aga", "A",course1, StudentUnit.GDANSK,12L);
		studentRepository.saveAll(List.of(student1,student2));

		mockMvc.perform(delete("/students?name=Magdalena"))
				.andExpect(status().isNoContent());

		assertTrue(studentRepository.getAllByName("Magdalena").isEmpty());
	}
	@SneakyThrows
	@Test
	void givenStudentsWithDifferentName_whenDeleteByNameNotExistingName_ThenRespondWithBadRequest() throws Exception {
		mockMvc.perform(delete("/students?name=hhhh"))
				.andExpect(status().isBadRequest());
	}

	@SneakyThrows
	@Test
	void givenStudentsWithDifferentSurname_whenGetBySurname_thenReturnStudent() {
		var course1 = new Course(1,"course1",0,new ArrayList<>(),"Aaa");
		courseRepository.save(course1);
		var student1 = new Student(UUID.randomUUID(),"Magdalena","C", course1, StudentUnit.GDANSK,11L);
		var student2 = new Student(UUID.randomUUID(),"Aga", "A",course1, StudentUnit.GDANSK,12L);
		var student3 = new Student(UUID.randomUUID(),"Aga", "A",course1, StudentUnit.WARSZAWA,12L);
		studentRepository.saveAll(List.of(student1,student2, student3));

		var response = mockMvc.perform(get("/students/surname/C"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();
		var studentList = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<StudentDto>>() {});
		assertEquals(1,studentList.size());
		var returnedStudent = studentList.get(0);
		assertEquals(student1.getId(), returnedStudent.id());

	}
}

package com.example.mprprojectmvn;

import com.example.mprprojectmvn.student.exceptionhandler.RecordNotFoundException;
import com.example.mprprojectmvn.student.resource.StudentResource;
import com.example.mprprojectmvn.student.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = StudentResource.class)
class StudentResourceWebMvcTest {

    @MockBean
    private StudentService studentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenNoStudents_whenGetById_thenRespondWithNotFound() throws Exception{
        when(studentService.getStudentById(any())).thenThrow(new RecordNotFoundException("not found"));
        var response = mockMvc.perform(get("/students/ad63cd7a-59bf-4a28-9d1f-6a8a4ebd2328")).andDo(print()).andReturn().getResponse();
        assertEquals(response.getStatus(), 404);
        assertTrue(response.getContentAsString().contains("not found"));

    }

}

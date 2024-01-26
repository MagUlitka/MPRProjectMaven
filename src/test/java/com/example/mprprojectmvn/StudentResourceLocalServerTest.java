package com.example.mprprojectmvn;

import com.example.mprprojectmvn.student.exceptionhandler.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentResourceLocalServerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void givenNoStudent_whenGetById_ThenReturnNotFound(){
       var responseEntity =  restTemplate.getForEntity("http://localhost:" + port +"/students/ad63cd7a-59bf-4a28-9d1f-6a8a4ebd2328", ErrorResponse.class);
       assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(404));
       var errorResponse = responseEntity.getBody();
       assertTrue(errorResponse.message().contains("not found"));
       assertNotNull(errorResponse.id());
        assertNotNull(errorResponse.timestamp());

    }
}

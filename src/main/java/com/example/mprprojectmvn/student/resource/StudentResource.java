package com.example.mprprojectmvn.student.resource;


import com.example.mprprojectmvn.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/students")
@RequiredArgsConstructor
public class StudentResource {
    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> getAll(){
       return studentService.getAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudents(@Validated @RequestBody CreateStudent createStudent){
        studentService.saveStudent(createStudent);
    }

    @PostMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto updateStudentById(@Validated @RequestBody StudentDto studentDto, @PathVariable UUID id){
        return studentService.updateStudentById(studentDto, id);
    }
    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable UUID id){
        return studentService.getStudentById(id);

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByName(String name){
        studentService.deleteByName(name);
    }

    @GetMapping("/name/{name}")
    public List<StudentDto> getStudentsByName(@PathVariable String name){
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/surname/{surname}")
    public List<StudentDto> getStudentBySurname(@PathVariable String surname){
        return studentService.getStudentsBySurname(surname);
    }

    @GetMapping("/courseName/{courseName}")
    public List<StudentDto> getStudentByCourseName(@PathVariable String courseName){
        return studentService.getStudentsByCourseName(courseName);
    }
}

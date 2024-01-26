package com.example.mprprojectmvn.student.resource;

import com.example.mprprojectmvn.course.data.CourseRepository;
import com.example.mprprojectmvn.student.data.Student;
import com.example.mprprojectmvn.student.data.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentMapper{

    private final CourseRepository courseRepository;

    public StudentDto toDto(Student student){
        var course = student.getCourse();
        return new StudentDto(student.getId(),student.getName(), student.getSurname(), course.getCourseName(), student.getUnit(),student.getIndex());
    }
    public Student toEntity(CreateStudent createStudent){
        var course = courseRepository.getCourseByName(createStudent.getCourseName());
        return new Student(createStudent.getName(), createStudent.getSurname(),course, createStudent.getUnit());
    }
    public Student studentDtoToEntity(StudentDto studentDto) {
        var course = courseRepository.getCourseByName(studentDto.courseName());
        return new Student(studentDto.id(),studentDto.name(),studentDto.surname(),course,studentDto.unit(), studentDto.index());
    }
}

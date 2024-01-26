package com.example.mprprojectmvn.course.resource;

import com.example.mprprojectmvn.student.data.Student;
import com.example.mprprojectmvn.student.resource.StudentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseDto {
    Integer courseId;
    @NotBlank String courseName;
    Integer totalStudentsCount = 0;
    List<StudentDto> attendingStudents = new ArrayList<>();
    String teacherName;
}

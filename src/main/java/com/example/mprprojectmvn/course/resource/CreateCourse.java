package com.example.mprprojectmvn.course.resource;

import com.example.mprprojectmvn.course.data.Course;
import com.example.mprprojectmvn.student.data.Student;
import com.example.mprprojectmvn.student.data.StudentUnit;
import com.example.mprprojectmvn.student.resource.StudentDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourse {
    @NotBlank String courseName;
    @NotNull Integer totalStudentsCount = 0;
    List<StudentDto> attendingStudents = new ArrayList<>();
    @NotBlank
    String teacherName;
}

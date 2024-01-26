package com.example.mprprojectmvn.student.resource;

import com.example.mprprojectmvn.course.data.Course;
import com.example.mprprojectmvn.course.resource.CourseDto;
import com.example.mprprojectmvn.student.data.StudentUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudent {
    @NotBlank String name;
    @NotBlank String surname;
    @NotNull String courseName;
    @NotNull StudentUnit unit;
}

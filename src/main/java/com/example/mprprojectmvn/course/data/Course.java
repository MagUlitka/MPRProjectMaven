package com.example.mprprojectmvn.course.data;

import com.example.mprprojectmvn.student.data.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Course {
    @Id
    @GeneratedValue
    @JsonProperty("course_id")
    Integer courseId;
    @Setter
    @JsonProperty("course_name")
    String courseName;
    @Setter
    @JsonProperty("total_students_count")
    Integer totalStudentsCount = 0;
    @OneToMany(mappedBy = "course")

    @Setter
    @JsonProperty("attending_students")
    List<Student> attendingStudents;
    @Setter
    @JsonProperty("teacher_name")
    String teacherName;

    public Course(String courseName, Integer totalStudentsCount, List<Student> attendingStudents, String teacherName) {
        this.courseName = courseName;
        this.totalStudentsCount = totalStudentsCount;
        this.attendingStudents = attendingStudents;
        this.teacherName = teacherName;
    }
}

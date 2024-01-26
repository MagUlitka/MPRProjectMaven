package com.example.mprprojectmvn.student.data;

import com.example.mprprojectmvn.course.data.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    public Student(String name, StudentUnit unit, Long index) {
        this.name = name;
        this.unit = unit;
        this.index = index;
    }
    public Student(String name, StudentUnit unit) {
        this.name = name;
        this.unit = unit;
    }

    public Student(String name, String surname, Course course, StudentUnit unit, Long index) {
        this.name = name;
        this.surname = surname;
        this.course = course;
        this.unit = unit;
        this.index = index;
    }

    public Student(String name, String surname, Course course, StudentUnit unit) {
        this.name = name;
        this.surname = surname;
        this.course = course;
        this.unit = unit;
    }

    @Id
    @GeneratedValue
    private UUID id;
    @Setter
    private String name;
    @Setter
    private String surname;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @Setter
    private Course course;
    @Setter
    @Enumerated(EnumType.STRING)
    private StudentUnit unit;
    @Setter
    private Long index;

}

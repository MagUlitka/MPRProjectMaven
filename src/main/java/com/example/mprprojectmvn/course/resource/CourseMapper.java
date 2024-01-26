package com.example.mprprojectmvn.course.resource;

import com.example.mprprojectmvn.course.data.Course;
import com.example.mprprojectmvn.student.data.Student;
import com.example.mprprojectmvn.student.resource.CreateStudent;
import com.example.mprprojectmvn.student.resource.StudentDto;
import com.example.mprprojectmvn.student.resource.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper {
    private final StudentMapper studentMapper;
    public CourseDto toDto(Course course){
        var students = course.getAttendingStudents().stream().map(studentMapper::toDto).toList();
        return new CourseDto(course.getCourseId(),course.getCourseName(),course.getTotalStudentsCount(),students,course.getTeacherName());
    }
    public Course toEntity(CreateCourse createCourse){
        var students = createCourse.getAttendingStudents().stream().map(studentMapper::studentDtoToEntity).toList();
        return new Course(createCourse.getCourseName(),createCourse.getTotalStudentsCount(),students,createCourse.getTeacherName());
    }
    public Course courseDtoToEntity(CourseDto courseDto) {
        var students = courseDto.getAttendingStudents().stream().map(studentMapper::studentDtoToEntity).toList();
        return new Course(courseDto.courseId, courseDto.courseName, courseDto.totalStudentsCount, students, courseDto.teacherName);
    }
}

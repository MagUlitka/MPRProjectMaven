package com.example.mprprojectmvn.course.service;

import com.example.mprprojectmvn.course.data.Course;
import com.example.mprprojectmvn.course.data.CourseRepository;
import com.example.mprprojectmvn.course.resource.CourseDto;
import com.example.mprprojectmvn.course.resource.CourseMapper;
import com.example.mprprojectmvn.course.resource.CreateCourse;
import com.example.mprprojectmvn.student.data.Student;
import com.example.mprprojectmvn.student.data.StudentRepository;
import com.example.mprprojectmvn.student.data.StudentUnit;
import com.example.mprprojectmvn.student.exceptionhandler.InvalidStudentNameException;
import com.example.mprprojectmvn.student.exceptionhandler.RecordNotFoundException;
import com.example.mprprojectmvn.student.resource.CreateStudent;
import com.example.mprprojectmvn.student.resource.StudentDto;
import com.example.mprprojectmvn.student.resource.StudentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;

    public List<CourseDto> getAll(){
        var courses = courseRepository.findAll();
        return courses.stream().map(courseMapper::toDto).toList();
    }

    public Course saveCourse(CreateCourse createCourse){
        var toSave = courseMapper.toEntity(createCourse);
        courseRepository.save(toSave);
        return toSave;
    }

    public void addAttendingStudent(CourseDto courseDto, CreateStudent createStudent){
        var students = new ArrayList<>(courseDto.getAttendingStudents().stream().map(studentMapper::studentDtoToEntity).toList());
        var studentToAdd = studentMapper.toEntity(createStudent);
        students.add(studentToAdd);
        var course = courseMapper.courseDtoToEntity(courseDto);
        course.setAttendingStudents(students);
        course.setTotalStudentsCount(courseDto.getTotalStudentsCount() + 1);
        courseRepository.save(course);
    }
    public CourseDto getCourseById(Integer id){
        return courseRepository.findById(id).map(courseMapper::toDto).orElseThrow(() -> new RecordNotFoundException("Course with id " + id + " not found."));
    }

    public void deleteCourseByName(String name){
        var courseByName = courseRepository.getCourseByName(name);
        if(courseByName == null){
            throw new InvalidStudentNameException("Course with name=" + name + " does not exist.");
        }
        courseRepository.delete(courseByName);
    }

    public CourseDto getCourseByName(String name) {
        var course = courseRepository.getCourseByName(name);
        return courseMapper.toDto(course);
    }

    public List<CourseDto> getCoursesByTotalStudentsCountGreaterThanEqual(Integer quantity){
        return courseRepository.getCoursesByTotalStudentsCountGreaterThanEqual(quantity).stream().map(courseMapper::toDto).toList();
    }

    public List<CourseDto> getCoursesByTotalStudentsCountLessThan(Integer quantity){
        return courseRepository.getCoursesByTotalStudentsCountLessThan(quantity).stream().map(courseMapper::toDto).toList();
    }

    public List<CourseDto> getCoursesByTeacherName(String name){
        return courseRepository.getCoursesByTeacherName(name).stream().map(courseMapper::toDto).toList();
    }

    List<StudentDto> getAttendingStudentsByCourseName(String courseName){
        return courseRepository.getAttendingStudentsByCourseName(courseName).stream().map(studentMapper::toDto).toList();

    }

    @Transactional
    public CourseDto updateCourseById(CourseDto courseDto, Integer id){
        Course toUpdate = courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("There's no such course in the database"));
        toUpdate.setCourseName(courseDto.getCourseName());
        toUpdate.setTotalStudentsCount(courseDto.getTotalStudentsCount());
        toUpdate.setAttendingStudents(courseDto.getAttendingStudents().stream().map(studentMapper::studentDtoToEntity).toList());
        toUpdate.setTeacherName(courseDto.getTeacherName());
        courseRepository.save(toUpdate);
        return courseDto;
    }
}

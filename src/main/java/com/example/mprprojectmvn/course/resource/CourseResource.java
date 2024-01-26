package com.example.mprprojectmvn.course.resource;

import com.example.mprprojectmvn.course.data.Course;
import com.example.mprprojectmvn.course.service.CourseService;
import com.example.mprprojectmvn.student.resource.CreateStudent;
import com.example.mprprojectmvn.student.resource.StudentDto;
import com.example.mprprojectmvn.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/courses")
@RequiredArgsConstructor
public class CourseResource {
    private final CourseService courseService;

    @GetMapping
    public List<CourseDto> getAll(){
        return courseService.getAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCourses(@Validated @RequestBody CreateCourse createCourse){
        courseService.saveCourse(createCourse);
    }

    @PostMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto updateCourseById(@Validated @RequestBody CourseDto courseDto, @PathVariable Integer id){
        return courseService.updateCourseById(courseDto, id);
    }
    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable Integer id){
        return courseService.getCourseById(id);

    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByName(String name){
        courseService.deleteCourseByName(name);
    }

    @GetMapping("/name/{name}")
    public CourseDto getCourseByName(@PathVariable String name){
        return courseService.getCourseByName(name);
    }
}

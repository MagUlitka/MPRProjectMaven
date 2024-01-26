package com.example.mprprojectmvn.student.service;

import com.example.mprprojectmvn.course.data.CourseRepository;
import com.example.mprprojectmvn.course.resource.CourseMapper;
import com.example.mprprojectmvn.course.service.CourseService;
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

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    public List<StudentDto> getAll(){
        var students = studentRepository.findAll();
        return students.stream().map(studentMapper::toDto).toList();
    }

    @Transactional
    public Student saveStudent(CreateStudent createStudent){
        var toSave = studentMapper.toEntity(createStudent);
        var index = createIndex(createStudent.getUnit());
        toSave.setIndex(index);
        studentRepository.save(toSave);
        var course = courseRepository.getCourseByName(createStudent.getCourseName());
        var courseDto = courseMapper.toDto(course);
        courseService.addAttendingStudent(courseDto, createStudent);
        return toSave;
    }
    public StudentDto getStudentById(UUID id){
        return studentRepository.findById(id).map(studentMapper::toDto).orElseThrow(() -> new RecordNotFoundException("Student with id " + id + " not found."));
    }

    public void deleteByName(String name){
        var studentByName = studentRepository.getAllByName(name);
        if(studentByName.isEmpty()){
            throw new InvalidStudentNameException("Student with name=" + name + " does not exist.");
        }
        studentRepository.deleteAll(studentByName);
    }

    private Long createIndex(StudentUnit unit) {
        var maxIndex = studentRepository.getMaxIndex().orElse(1L);
        if(StudentUnit.GDANSK.equals(unit)){
            return 5 + maxIndex;
        }
        else {
            return 10 + maxIndex;
        }
    }

    public List<StudentDto> getStudentsByName(String name) {
        return studentRepository.getAllByName(name).stream().map(studentMapper::toDto).toList();
    }

    public List<StudentDto> getStudentsBySurname(String surname){
        return studentRepository.getStudentsBySurname(surname).stream().map(studentMapper::toDto).toList();
    }
    public List<StudentDto> getStudentsByCourseName(String courseName){
        return studentRepository.getStudentsByCourseName(courseName).stream().map(studentMapper::toDto).toList();
    }

    @Transactional
    public StudentDto updateStudentById(StudentDto studentDto, UUID id){
        Student toUpdate = studentRepository.findById(studentDto.id()).orElseThrow(() -> new RecordNotFoundException("There's no such student in the database"));
        toUpdate.setName(studentDto.name());
        toUpdate.setSurname(studentDto.surname());
        var currentCourse = toUpdate.getCourse();
        var newCourse = courseRepository.getCourseByName(studentDto.courseName());
        toUpdate.setCourse(newCourse);
        currentCourse.setTotalStudentsCount(currentCourse.getTotalStudentsCount()-1);
        newCourse.setTotalStudentsCount(newCourse.getTotalStudentsCount()+1);

        toUpdate.setUnit(studentDto.unit());
        studentRepository.save(toUpdate);
        return studentDto;
    }
}

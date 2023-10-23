package com.example.mprprojectmvn.data;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class StudentRepository {

    public StudentRepository() {
        var student = new Student(UUID.fromString("6f16f034-de79-47ad-b05f-77ec29b7a453"),"Magdalena", StudentUnit.GDANSK, 3L);
        students.add(student);
    }
    private final List<Student> students = new ArrayList<>();

    public void saveStudent(Student student){
        students.add(student);
    }
    public Student getStudentById(UUID id){
        return students.stream()
                .filter(it -> it.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Long getMaxIndex(){
        return students.stream()
                .map(Student::index)
                .max(Comparator.naturalOrder())
                .orElse(0L);
    }
}

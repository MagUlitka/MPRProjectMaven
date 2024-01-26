package com.example.mprprojectmvn.course.data;

import com.example.mprprojectmvn.student.data.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Modifying
    @Transactional
    @Query("delete from Course c where c.courseName = :name")
    void deleteCourseByName(@Param("name") String name);
    @Query("select c from Course c")
    List<Course> getAllCourses();
    @Query("select c from Course c where c.courseName = :courseName")
    List<Course> getAllByName(@Param("courseName") String courseName);
//    @Query("select c from Course c where c.courseName = :name")
//    List<Course> getCoursesByName(@Param("name") String name);
    @Query("select c from Course c where c.courseName = :name")
    Course getCourseByName(@Param("name") String name);
    @Query("select c from Course c where c.totalStudentsCount >= :quantity")
    List<Course> getCoursesByTotalStudentsCountGreaterThanEqual(@Param("quantity") Integer quantity);
    @Query("select c from Course c where c.totalStudentsCount < :quantity")
    List<Course> getCoursesByTotalStudentsCountLessThan(@Param("quantity") Integer quantity);
    @Query("select c from Course c where c.teacherName = :name")
    List<Course> getCoursesByTeacherName(@Param("name") String name);
    @Query("select s from Student s where s.course.courseName = :courseName")
    List<Student> getAttendingStudentsByCourseName(@Param("courseName") String courseName);
}

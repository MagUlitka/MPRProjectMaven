package com.example.mprprojectmvn.student.data;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Modifying
    @Transactional
    @Query("delete from Student s where s.name = :name")
    void deleteByName(@Param("name") String name);

    @Query("select max(s.index) from Student s")
    Optional<Long> getMaxIndex();

@Query("select s from Student s")
    List<Student> getAll();

    @Query("select s from Student s where s.name = :name and s.unit = :unit")
    List<Student> getByNameAndUnit(@Param("name") String name, @Param("unit") StudentUnit unit);
    @Query("select s from Student s where s.surname = :surname")
    List<Student> getStudentsBySurname(@Param("surname") String surname);
    @Query("select s from Student s where s.course = :courseName")
    List<Student> getStudentsByCourseName(@Param("courseName") String courseName);
    default  List<Student> getFromGdanskByName(String name) {
        return getByNameAndUnit(name,StudentUnit.GDANSK);
    }
    @Query("select s from Student s where s.name = :name")
    List<Student> getAllByName(@Param("name") String name);

}

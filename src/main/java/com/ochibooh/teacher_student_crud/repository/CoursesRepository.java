package com.ochibooh.teacher_student_crud.repository;

import com.ochibooh.teacher_student_crud.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {
    List<Courses> findByStudentIdOrderByIdDesc(String studentId);

    List<Courses> findByStudentIdAndCourseName(String studentId, String courseName);
}

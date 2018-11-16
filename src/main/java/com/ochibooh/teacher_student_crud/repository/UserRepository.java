package com.ochibooh.teacher_student_crud.repository;

import com.ochibooh.teacher_student_crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);

    int countAllByRole(String role);

    List<User> findByRoleAndEmailNotOrderByIdDesc(String role, String email);
}
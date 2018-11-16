package com.ochibooh.teacher_student_crud.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "student_id")
    @NotNull
    private String studentId;

    @Column(name = "course_name")
    @NotNull
    private String courseName;

    @Column(name = "active")
    @NotNull
    @ColumnDefault("1")
    private int active;

    @Column(name = "reg_date")
    @NotNull
    private String registrationDate;
}

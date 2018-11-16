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
@Table(name = "tbl_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "fname")
    @NotNull
    private String firstName;

    @Column(name = "lname")
    @NotNull
    private String lastName;

    @Column(name = "active")
    @NotNull
    @ColumnDefault("1")
    private int active;

    @Column(name = "user_role")
    @NotNull
    private String role;
}
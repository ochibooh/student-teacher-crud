package com.ochibooh.teacher_student_crud.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "* Please provide an email")
    @NotNull
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "* Please provide your password")
    @NotNull
    private String password;

    @Column(name = "fname")
    @NotEmpty(message = "* Please provide your first name")
    @NotNull
    private String firstName;

    @Column(name = "lname")
    @NotEmpty(message = "* Please provide your last name")
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
package com.ochibooh.teacher_student_crud.service;

import com.ochibooh.teacher_student_crud.entity.Courses;
import com.ochibooh.teacher_student_crud.entity.RequestResponse;
import com.ochibooh.teacher_student_crud.entity.User;
import com.ochibooh.teacher_student_crud.repository.CoursesRepository;
import com.ochibooh.teacher_student_crud.repository.UserRepository;
import com.ochibooh.teacher_student_crud.util.DateTimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private CoursesRepository mockCoursesRepository;

    private UserService userServiceUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        userServiceUnderTest = new UserService(mockUserRepository, mockCoursesRepository);

        List<User> users = new ArrayList<>();
        users.add(User.builder().id((long)1).firstName("Test").lastName("Student").email("test-student@mail.com").password(new BCryptPasswordEncoder().encode("534564")).role("STUDENT").active(0).build());
        users.add(User.builder().id((long)2).firstName("Test").lastName("Teacher").email("test-teacher@mail.com").password(new BCryptPasswordEncoder().encode("56212454654")).role("TEACHER").active(0).build());

        Mockito.when(mockUserRepository.save(any())).thenReturn(users.get(0));
        Mockito.when(mockUserRepository.findByEmail(anyString())).thenReturn(users);
        Mockito.when(mockUserRepository.countAllByRole(anyString())).thenReturn(users.size());
        Mockito.when(mockUserRepository.findByRoleAndEmailNotOrderByIdDesc(anyString(), anyString())).thenReturn(users);
        Mockito.when(mockUserRepository.getOne(any(Long.class))).thenReturn(users.get(0));

        List<Courses> courses = new ArrayList<>();
        courses.add(Courses.builder().id((long)1).studentId("1").courseName("Comp 101").active(1).registrationDate(new DateTimeUtil().getCurrentDateTime()).build());
        courses.add(Courses.builder().id((long)2).studentId("1").courseName("Math 101").active(1).registrationDate(new DateTimeUtil().getCurrentDateTime()).build());
        courses.add(Courses.builder().id((long)3).studentId("1").courseName("Bio 101").active(1).registrationDate(new DateTimeUtil().getCurrentDateTime()).build());

        Mockito.when(mockCoursesRepository.findByStudentIdOrderByIdDesc(anyString())).thenReturn(courses);
        Mockito.when(mockCoursesRepository.findByStudentIdAndCourseName(anyString(), anyString())).thenReturn(new ArrayList<>());
        Mockito.when(mockCoursesRepository.getOne(any(Long.class))).thenReturn(courses.get(0));
    }

    @Test
    public void testFindUserByEmail() {
        final String email = "phelix-temp@mail.com";
        final List<User> users = userServiceUnderTest.findUserByEmail(email);
        if (users != null && users.size() > 0){
            LoggerFactory.getLogger(LoggerFactory.class).debug("FIND USER TEST DATA :: " + users.toString());
        } else {
            LoggerFactory.getLogger(LoggerFactory.class).debug("FIND USER TEST DATA :: No users available");
        }
    }

    @Test
    public void testSaveUser() {
        User user = User.builder().firstName("Test").lastName("User").email("test@mail.com").password("123456").role("STUDENT").active(0).build();
        RequestResponse requestResponse = userServiceUnderTest.saveUser(user);
        LoggerFactory.getLogger(LoggerFactory.class).debug("SAVE USER TEST DATA :: " + new BCryptPasswordEncoder().encode("123456"));
        LoggerFactory.getLogger(LoggerFactory.class).debug("SAVE USER TEST DATA :: " + requestResponse.toString());
    }

    @Test
    public void testCountUserByRole(){
        int count = userServiceUnderTest.getUsersCount("STUDENT");
        LoggerFactory.getLogger(LoggerFactory.class).debug("COUNT USERS TEST :: " + count);
    }

    @Test
    public void testGetUsersByRoleNotLoggedIn(){
        List<User> users = userServiceUnderTest.getUsersByRoleNotLoggedIn("STUDENT", "test-teacher@mail.com");
        LoggerFactory.getLogger(LoggerFactory.class).debug("USERS BY ROLE NOT LOGGED IN TEST :: " + users);
    }

    @Test
    public void testFindUserById(){
        User user = userServiceUnderTest.getUserById((long)2);
        LoggerFactory.getLogger(LoggerFactory.class).debug("GET USER BY ID TEsT DATA :: " + user.toString());
    }

    @Test
    public void testUpdateUser(){
        User user = User.builder().firstName("update").lastName("test").id((long)1).email("test-update").build();
        RequestResponse requestResponse = userServiceUnderTest.updateUserDetails(user);
        LoggerFactory.getLogger(LoggerFactory.class).debug("USER UPDATE TEST DATA :: " + requestResponse.toString());
    }

    @Test
    public void testUpdateUserStatus(){
        User user = User.builder().id((long) 1).active(1).build();
        RequestResponse requestResponse = userServiceUnderTest.updateUserStatus(user);
        LoggerFactory.getLogger(LoggerFactory.class).debug("USER UPDATE STATUS TEST DATA :: " + requestResponse.toString());
    }

    @Test
    public void findStudentCourses(){
        List<Courses> courses = userServiceUnderTest.findCoursesByStudentId("1");
        LoggerFactory.getLogger(LoggerFactory.class).debug("STUDENT COURSES TEST DATA :: " + courses.toString());
    }

    @Test
    public void registerCourseTest(){
        RequestResponse requestResponse = userServiceUnderTest.registerCourse(Courses.builder().studentId("1").courseName("Comp 102").registrationDate(new DateTimeUtil().getCurrentDateTime()).active(1).build());
        LoggerFactory.getLogger(LoggerFactory.class).debug("REGISTER COURSES TEST DATA :: " + requestResponse.toString());
    }

    @Test
    public void testChangeCourseStatus(){
        RequestResponse requestResponse = userServiceUnderTest.updateCourseStatus(Courses.builder().id((long)1).active(0).build());
        LoggerFactory.getLogger(LoggerFactory.class).debug("UPDATE COURSE STATUS TEST DATA :: " + requestResponse.toString());
    }
}

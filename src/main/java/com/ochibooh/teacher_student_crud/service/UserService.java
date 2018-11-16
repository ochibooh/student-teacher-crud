package com.ochibooh.teacher_student_crud.service;

import com.ochibooh.teacher_student_crud.entity.Courses;
import com.ochibooh.teacher_student_crud.entity.RequestResponse;
import com.ochibooh.teacher_student_crud.entity.User;
import com.ochibooh.teacher_student_crud.repository.CoursesRepository;
import com.ochibooh.teacher_student_crud.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserService {
    private UserRepository userRepository;

    private CoursesRepository coursesRepository;

    @Autowired
    public UserService(UserRepository userRepository, CoursesRepository coursesRepository) {
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
    }

    public List<User> findUserByEmail(String email){
        List<User> users = this.userRepository.findByEmail(email);
        if (users.size() == 0){
            return new ArrayList<>();
        } else {
            boolean exists = false;
            for (User user: users) {
                if (user.getEmail().equals(email)){
                    exists = true;
                }
            }
            if (exists){
                return users;
            } else {
                return new ArrayList<>();
            }
        }
    }

    public RequestResponse saveUser(User user) {
        if (this.findUserByEmail(user.getEmail()) != null && this.findUserByEmail(user.getEmail()).size() == 0){
            try {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                user.setActive(1);
                userRepository.save(user);
                return RequestResponse.builder().responseCode(0).data(user).message("success").build();
            } catch (Exception ignored){
                LoggerFactory.getLogger(LoggerFactory.class).error("SAVE USER ERROR", ignored);
                return RequestResponse.builder().responseCode(1).data(user).message("Sorry, error encountered while saving details. Please try again").build();
            }
        } else {
            return RequestResponse.builder().responseCode(1).data(user).message("Sorry, this email address is already in use by another user. please try using another email address").build();
        }
    }

    public int getUsersCount(String role){
        return userRepository.countAllByRole(role);
    }

    public List<User> getUsersByRoleNotLoggedIn(String role, String email){
        return userRepository.findByRoleAndEmailNotOrderByIdDesc(role, email);
    }

    public User getUserById(long id){
        return userRepository.getOne(id);
    }

    public RequestResponse updateUserDetails(User user){
        User userToUpdate = this.getUserById(user.getId());
        if (userToUpdate != null){
            try {
                userToUpdate.setEmail(user.getEmail());
                userToUpdate.setFirstName(user.getFirstName());
                userToUpdate.setLastName(user.getLastName());
                userRepository.save(userToUpdate);
                return RequestResponse.builder().responseCode(0).data(userToUpdate).message("success").build();
            } catch (Exception ignored){
                LoggerFactory.getLogger(LoggerFactory.class).error("UPDATE USER ERROR", ignored);
                return RequestResponse.builder().responseCode(1).data(user).message("Sorry, error encountered while updating details. Please try again").build();
            }
        } else {
            return RequestResponse.builder().responseCode(1).data(null).message("Sorry, this user doest not exists. Please try again").build();
        }
    }

    public RequestResponse updateUserStatus(User user){
        User userToChangeStatus = this.getUserById(user.getId());
        if (userToChangeStatus != null){
            try {
                userToChangeStatus.setActive(user.getActive());
                userRepository.save(userToChangeStatus);
                return RequestResponse.builder().responseCode(0).data(userToChangeStatus).message("success").build();
            } catch (Exception ignored){
                LoggerFactory.getLogger(LoggerFactory.class).error("UPDATE USER ERROR", ignored);
                return RequestResponse.builder().responseCode(1).data(user).message("Sorry, error encountered while updating details. Please try again").build();
            }
        } else {
            return RequestResponse.builder().responseCode(1).data(null).message("Sorry, this user doest not exists. Please try again").build();
        }
    }

    public List<Courses> findCoursesByStudentId(String studentId){
        List<Courses> courses = coursesRepository.findByStudentIdOrderByIdDesc(studentId);
        if (courses.size() > 0){
            return courses;
        } else {
            return new ArrayList<>();
        }
    }

    public List<Courses> findByCourseNameAndStudentId(String studentId, String courseName){
        List<Courses> courses = coursesRepository.findByStudentIdAndCourseName(studentId, courseName);
        if (courses.size() > 0){
            return courses;
        } else {
            return new ArrayList<>();
        }
    }

    public RequestResponse registerCourse(Courses courses){
        if (this.findByCourseNameAndStudentId(courses.getStudentId(), courses.getCourseName()) != null && this.findByCourseNameAndStudentId(courses.getStudentId(), courses.getCourseName()).size() == 0){
            try {
                coursesRepository.save(courses);
                return RequestResponse.builder().responseCode(0).data(courses).message("success").build();
            } catch (Exception e){
                LoggerFactory.getLogger(LoggerFactory.class).error("SAVE COURSE ERROR", e);
                return RequestResponse.builder().responseCode(1).data(courses).message("Sorry, error encountered while registering course. Please try again").build();
            }
        } else {
            return RequestResponse.builder().responseCode(1).data(courses).message("Sorry, you are already registered to this course. Please try another").build();
        }
    }

    public Courses findCourseById(long id){
        return coursesRepository.getOne(id);
    }

    public RequestResponse updateCourseStatus(Courses courses){
        Courses courseToChangeStatus = this.findCourseById(courses.getId());
        if (courseToChangeStatus != null){
            try {
                courseToChangeStatus.setActive(courses.getActive());
                coursesRepository.save(courseToChangeStatus);
                return RequestResponse.builder().responseCode(0).data(courseToChangeStatus).message("success").build();
            } catch (Exception ignored){
                LoggerFactory.getLogger(LoggerFactory.class).error("UPDATE USER ERROR", ignored);
                return RequestResponse.builder().responseCode(1).data(courses).message("Sorry, error encountered while course details. Please try again").build();
            }
        } else {
            return RequestResponse.builder().responseCode(1).data(null).message("Sorry, this course doest not exists. Please try again").build();
        }
    }
}

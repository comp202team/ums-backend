package com.comp202.ums.data;

import com.comp202.ums.Entity.*;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.DepartmentRepository;
import com.comp202.ums.Repository.EnrollmentRepository;
import com.comp202.ums.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class DataInitCreateUserRole implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public void run(String... args) throws Exception {
        Department dbDept = departmentRepository.getDepartmentByDepartmentCode("COMP");
        User dbStudentUser = userRepository.findByUsername("student1");
        User dbInstructorUser = userRepository.findByUsername("instructor");
        Course dbCourse= courseRepository.findByCourseCode("COMP202").orElse(null);
        Enrollment dbenrollment = enrollmentRepository.getByEnrollmentId(1L);

        if (dbDept==null){
            Department department = Department.builder()
                    .departmentName("ComputerEngineering")
                    .departmentCode("COMP")
                    .departmentHead("Çağrı Güngör")
                    .build();
            departmentRepository.save(department);
        }
        if(dbStudentUser == null){
            Department department = departmentRepository.getDepartmentByDepartmentCode("COMP");
            User studentUser = User.builder().username("student")
                    .role(Role.STUDENT)
                    .password(bCryptPasswordEncoder.encode("password"))
                    .email("student@gmail.com")
                    .firstName("Berke")
                    .lastName("Yıldırım")
                    .department(department)
                    .build();
            userRepository.save(studentUser);
        }

        if(dbInstructorUser == null){
            Department department = departmentRepository.getDepartmentByDepartmentCode("COMP");
            User instructorUser = User.builder().username("instructor")
                    .role(Role.INSTRUCTOR)
                    .password(bCryptPasswordEncoder.encode("password"))
                    .email("instructor@gmail.com")
                    .firstName("Hüseyin")
                    .lastName("Akkaş")
                    .department(department)
                    .build();
            userRepository.save(instructorUser);
        }
        if(dbCourse==null){
            Course course = Course.builder().department(departmentRepository.getDepartmentByDepartmentCode("COMP"))
                    .courseCode("COMP202")
                    .courseName("SoftwareDevelopment")
                    .coursedesc("Software development")
                    .creditHours(5L)
                    .instructor(userRepository.findByUsername("instructor"))
                    .build();
            courseRepository.save(course);
        }
        if (dbenrollment==null){
            Enrollment enrollment = Enrollment.builder()
                    .student(userRepository.findByUsername("student"))
                    .course(courseRepository.getByCourseCode("COMP202"))
                    .build();
            enrollmentRepository.save(enrollment);
        }

    }





}
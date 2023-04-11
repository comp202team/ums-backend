package com.comp202.ums.data;

import com.comp202.ums.Entity.Role;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class DataInitCreateUserRole implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User dbStudentUser = userRepository.findByUsername("student");
        User dbInstructorUser = userRepository.findByUsername("instructor");

        if(dbStudentUser == null){
            User studentUser = User.builder().username("student")
                    .role(Role.STUDENT)
                    .password(bCryptPasswordEncoder.encode("password"))
                    .email("student@gmail.com")
                    .firstName("Berke")
                    .lastName("Yıldırım")
                    .build();
            userRepository.save(studentUser);
        }

        if(dbInstructorUser == null){
            User instructorUser = User.builder().username("instructor")
                    .role(Role.INSTRUCTOR)
                    .password(bCryptPasswordEncoder.encode("password"))
                    .email("instructor@gmail.com")
                    .firstName("Hüseyin")
                    .lastName("Akkaş")
                    .build();
            userRepository.save(instructorUser);
        }
    }





}
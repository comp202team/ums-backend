package com.comp202.ums.Entity;

import com.comp202.ums.Entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder


@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})})

@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted=false")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    private LocalDate dob;
    @ManyToOne
    @JoinColumn(name="department")
    private Department department;

    @Enumerated(EnumType.STRING)
    private Role role;

   }
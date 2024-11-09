package com.example.project_cruise.entity;

import com.example.project_cruise.model.User_Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String fullName;
    @Enumerated(EnumType.STRING)
    User_Role role;
    String phone;

    @Column(unique = true, nullable = false)
    String email;
    String password;
    String address;
    String avatar;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

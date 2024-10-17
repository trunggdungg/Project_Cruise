package com.example.project_cruise.entity;

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
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String title;

    @Column(unique = true, nullable = false)
    String slug;

    @Column(columnDefinition = "TEXT")
    String content;

    @Column(columnDefinition = "TEXT")
    String description;

    String thumbnail;
    Boolean status;
    LocalDateTime created_at;
    LocalDateTime updated_at;
    LocalDateTime published_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}

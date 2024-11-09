package com.example.project_cruise.repository;

import com.example.project_cruise.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findByType(String cruise);



}

package com.example.project_cruise.repository;

import com.example.project_cruise.entity.Cruise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CruiseRepository extends JpaRepository<Cruise, Integer> {
    boolean existsBySlug(String slug);
}

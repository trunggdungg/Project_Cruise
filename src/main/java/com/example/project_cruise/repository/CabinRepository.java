package com.example.project_cruise.repository;

import com.example.project_cruise.entity.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CabinRepository extends JpaRepository<Cabin, Integer> {
}

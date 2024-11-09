package com.example.project_cruise.repository;

import com.example.project_cruise.entity.Cruise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CruiseRepository extends JpaRepository<Cruise, Integer> {
    boolean existsBySlug(String slug);

    Optional<Cruise> findTagById(Integer id);

    Page<Cruise> findByNameContainingIgnoreCase(Pageable pageable, String searchTerm);

    Page<Cruise> findAll(Specification<Cruise> spec, Pageable pageable);
}

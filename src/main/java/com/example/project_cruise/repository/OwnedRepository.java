package com.example.project_cruise.repository;

import com.example.project_cruise.entity.Owned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnedRepository extends JpaRepository<Owned, Integer> {
}

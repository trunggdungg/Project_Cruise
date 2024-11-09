package com.example.project_cruise.repository;

import com.example.project_cruise.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query("SELECT DISTINCT l.locationName FROM Location l")
    List<String> findDistinctLocationName();
    // lay ra tat ca cac locationName  duy nhat

    List<Location> findAll();
}

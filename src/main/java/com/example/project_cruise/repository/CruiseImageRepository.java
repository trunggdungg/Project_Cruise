package com.example.project_cruise.repository;

import com.example.project_cruise.entity.CruiseImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CruiseImageRepository extends JpaRepository<CruiseImage, Integer> {
    List<CruiseImage> findByCruiseId(Integer cruiseId);
    CruiseImage findFirstByCruiseId(Integer cruiseId);

    List<CruiseImage> findByCruiseIdIn(List<Integer> cruiseIds);
}

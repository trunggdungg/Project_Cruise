package com.example.project_cruise.service;

import com.example.project_cruise.entity.Cruise;
import com.example.project_cruise.entity.CruiseImage;
import com.example.project_cruise.entity.Tag;
import com.example.project_cruise.repository.CruiseImageRepository;
import com.example.project_cruise.repository.CruiseRepository;
import com.example.project_cruise.repository.TagRepository;
import com.example.project_cruise.specification.CruiseSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CruiseService {
    private final CruiseRepository cruiseRepository;
    private final CruiseImageRepository cruiseImageRepository;

    public Page<Cruise> getAllCruises(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        return cruiseRepository.findAll(pageable);
    }

    public List<Tag> getTagByCruiseId(Integer id) {
        Cruise cruise = cruiseRepository.findTagById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid cruise Id:" + id));
        return cruise.getTags();
    }

    public List<Tag> getTagsByCruiseId(Integer cruiseId) {
        return cruiseRepository.findById(cruiseId)
            .map(Cruise::getTags)
            .orElseThrow(() -> new RuntimeException("Cruise not found"));
    }

    public List<Cruise> findCruisesByTag( List<String> tagIds) {
        Specification<Cruise> spec = CruiseSpecifications.findCruisesByTag(tagIds);
        return cruiseRepository.findAll((Sort) spec);
    }

    public Page<Cruise> searchCruises(int page, int size,String searchTerm) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        return cruiseRepository.findByNameContainingIgnoreCase(pageable, searchTerm);
    }

    public CruiseImage findCruiseImageByCruiseId(Integer integer) {
        return cruiseImageRepository.findFirstByCruiseId(integer);
    }

    public Page<Cruise> getCruisesWithFilters(String title, String location, Double fromPrice, Double toPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Specification<Cruise> spec = CruiseSpecifications.getCruisesWithFilters(title, location, fromPrice, toPrice);


        return cruiseRepository.findAll(spec, pageable);
    }
}

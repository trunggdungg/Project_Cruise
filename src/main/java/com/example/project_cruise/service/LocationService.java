package com.example.project_cruise.service;

import com.example.project_cruise.entity.Location;
import com.example.project_cruise.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<String> getAllLocationName() {
        return locationRepository.findDistinctLocationName();
    }

}

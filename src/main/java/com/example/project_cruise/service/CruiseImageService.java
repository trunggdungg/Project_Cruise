package com.example.project_cruise.service;

import com.example.project_cruise.entity.CruiseImage;
import com.example.project_cruise.repository.CruiseImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CruiseImageService {
    private final CruiseImageRepository cruiseImageRepository;

    public CruiseImage getImagesByCruiseId(Integer cruiseId) {
        return cruiseImageRepository.findFirstByCruiseId(cruiseId);
    }
    public Map<Integer, String> getCruiseImages(List<Integer> cruiseIds) {
        List<CruiseImage> cruiseImages = cruiseImageRepository.findByCruiseIdIn(cruiseIds);

        return cruiseImages.stream()
            .collect(Collectors.toMap(
                image -> image.getCruise().getId(),
                CruiseImage::getUrlImage,
                (existing, replacement) -> existing  //
            ));
    }
    public Map<Integer, String> getAllCruiseImages() {
        List<CruiseImage> cruiseImages = cruiseImageRepository.findAll(); // Lấy tất cả hình ảnh
        return cruiseImages.stream()
            .collect(Collectors.toMap(
                image -> image.getCruise().getId(),
                CruiseImage::getUrlImage,
                (existing, replacement) -> existing // Giữ lại giá trị hiện tại nếu có trùng lặp
            ));
    }

}

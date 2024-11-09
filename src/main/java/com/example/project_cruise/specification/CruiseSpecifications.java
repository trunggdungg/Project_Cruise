package com.example.project_cruise.specification;

import com.example.project_cruise.entity.Cruise;
import com.example.project_cruise.entity.Tag;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CruiseSpecifications {
    public static Specification<Cruise> findCruisesByTag(List<String> tagIds) {
        return (root, query, cb) -> {
            if (tagIds == null || tagIds.isEmpty()) {
                return null;
            }

            // Join với bảng tags
            Join<Cruise, Tag> tagJoin = root.join("tags");

            // Tạo predicate để check tag id nằm trong list tagIds
            return tagJoin.get("id").in(tagIds);
        };
    }

    //loc theo  title, location, minPrice, maxPrice
    public static Specification<Cruise> getCruisesWithFilters(String title, String location, Double fromPrice, Double toPrice) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null) {
                predicates.add(cb.like(root.get("name"), "%" + title + "%"));
            }

            // Kiểm tra nếu location không phải null
            if (location != null && !location.isEmpty()) {
                predicates.add(cb.like(root.get("location").get("locationName"), "%" + location + "%")); // Giả sử 'name' là trường của Location
            }

            if (fromPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), fromPrice));
            }

            if (toPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), toPrice));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
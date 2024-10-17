package com.example.project_cruise.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cruises")
public class Cruise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;

    @Column(unique = true, nullable = false)
    String slug;
    Integer launchedYear;
    Integer cabinQuantity;
    String material;
    @Column(columnDefinition = "TEXT")
    String description;
    @Column(name = "price", precision = 10, scale = 2)
    BigDecimal price;
    LocalTime departureTime;
    LocalTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    @ManyToOne
    @JoinColumn(name = "owned_id")
    Owned owned;

    LocalDateTime created_at;
    LocalDateTime updated_at;

    @ManyToMany
    @JoinTable(name = "cruise_rules",
        joinColumns = @JoinColumn(name = "rule_id"),
        inverseJoinColumns = @JoinColumn(name = "cruise_id"))
    List<Rule> rules;

    @ManyToMany
    @JoinTable(name = "cruise_tags",
        joinColumns = @JoinColumn(name = "cruise_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    List<Tag> tags;
}

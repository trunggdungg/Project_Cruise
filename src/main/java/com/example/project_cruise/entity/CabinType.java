package com.example.project_cruise.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cabin_types")
public class CabinType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    Integer roomSize;
    Integer numberGuests;

    @Column(name = "price", precision = 10, scale = 2)
    BigDecimal price;

    @ManyToMany
    @JoinTable(name = "cabin_type_tag",
            joinColumns = @JoinColumn(name = "cabin_type_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    List<Tag> tags;
}

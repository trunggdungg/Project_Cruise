package com.example.project_cruise.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cabins")
public class Cabin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer roomNumber;

    @ManyToOne
    @JoinColumn(name = "cruise_id")
    Cruise cruise;

    @ManyToOne
    @JoinColumn(name = "cabin_type_id")
    CabinType cabinType;

    Integer floor;

//    @ManyToMany
//    @JoinTable(
//        name = "booking_cabins",
//        joinColumns = @JoinColumn(name = "cabin_id"),
//        inverseJoinColumns = @JoinColumn(name = "booking_id")
//    )
//    List<Booking> bookings;
}

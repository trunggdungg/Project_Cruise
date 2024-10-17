package com.example.project_cruise.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    LocalDateTime bookingDate;
    Integer guestQuantity;
    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;
    String note;
    Boolean bookingStatus;
    LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "cruise_id")
    Cruise cruise;

    @ManyToMany
    @JoinTable(
        name = "booking_cabins",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "cabin_id")
    )
    List<Cabin> cabins;

}

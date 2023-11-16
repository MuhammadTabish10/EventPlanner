package com.EventPlanner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ticket_package")
public class TicketPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comboType;
    private Double totalPrice;
    private Double discountPrice;
    private Double price;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}

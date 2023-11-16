//package com.EventPlanner.model;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name = "standard_price")
//public class StandardPrice {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String ticketName;
//    private Double price;
//    private Boolean status;
//
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime startDateAndTime;
//
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime endDateAndTime;
//
//    @ManyToOne
//    @JoinColumn(name = "event_id")
//    private Event event;
//
//    @ManyToOne
//    @JoinColumn(name = "ticket_id"
//    private Account account;
//}

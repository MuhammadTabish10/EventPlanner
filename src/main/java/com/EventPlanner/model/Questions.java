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
@Table(name = "questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String type;
    private String description;
    private Boolean status;
    private Boolean isSystem;
    private Boolean appliesToOurAccount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}

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
@Table(name = "sponsor")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String logo;
    private String profile;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "sponsor_company_id")
    private Company sponsorCompany;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "SubAccount_id")
    private SubAccount subAccount;

    @ManyToOne
    @JoinColumn(name = "sponsor_type_id")
    private SponsorType sponsorType;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}

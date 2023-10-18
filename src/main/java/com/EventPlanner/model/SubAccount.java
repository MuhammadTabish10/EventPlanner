package com.EventPlanner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sub_account")
public class SubAccount {

//    @EmbeddedId
//    private SubAccountPK subAccountPK = new SubAccountPK();

    @EmbeddedId
    private SubAccountPK id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String phone;
    private String website;
    private Boolean status;

    @ManyToOne
    @MapsId("accountId") // Maps the accountId attribute of SubAccountPK
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}

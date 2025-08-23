package com.stayhome.healthcare.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "insurances")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "insurance_entry_id", updatable = false, nullable = false)
    private UUID insuranceEntryId;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "insurance_member_id", nullable = false)
    private String insuranceMemberId;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "plan_type", nullable = false)
    private String planType;

}

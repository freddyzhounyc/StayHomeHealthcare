package com.stayhome.healthcare.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "billings")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "billing_id", updatable = false, nullable = false)
    private UUID billingId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(name = "medical_service", nullable = false)
    private String medicalService;

    @Column(name = "billing_amount", nullable = false)
    private Double billingAmount;

    @Column(name = "percent_fulfilled", nullable = false)
    private Double percentFulfilled;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "date_fully_paid", nullable = false)
    private LocalDateTime dateFullyPaid;

}

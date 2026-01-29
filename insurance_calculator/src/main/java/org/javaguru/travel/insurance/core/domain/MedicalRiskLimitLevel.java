package org.javaguru.travel.insurance.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "medical_risk_limit_level")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRiskLimitLevel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medical_risk_limit_level_ic", nullable = false, length = 200)
    private String medicalRiskLimitLevelIc;

    @Column(name = "coefficient", nullable = false, precision = 10, scale = 2)
    private BigDecimal coefficient;
}

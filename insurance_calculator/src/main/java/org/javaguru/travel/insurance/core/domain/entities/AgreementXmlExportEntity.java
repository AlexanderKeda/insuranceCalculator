package org.javaguru.travel.insurance.core.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agreements_xml_export")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AgreementXmlExportEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agreement_uuid", nullable = false, columnDefinition = "CHAR(36)")
    private String agreementUuid;

    @Column(name = "already_exported", nullable = false)
    private Boolean alreadyExported;
}

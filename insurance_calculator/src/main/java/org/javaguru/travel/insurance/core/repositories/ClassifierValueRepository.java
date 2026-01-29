package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassifierValueRepository extends JpaRepository<ClassifierValue, Long> {

    Optional<ClassifierValue> findByClassifierTitleAndIc(
            String classifierTitle, String ic
    );

    boolean existsByClassifierTitleAndIc(String classifierTitle,String ic);
}

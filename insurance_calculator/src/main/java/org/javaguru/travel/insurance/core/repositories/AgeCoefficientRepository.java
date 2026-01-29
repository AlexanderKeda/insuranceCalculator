package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.AgeCoefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AgeCoefficientRepository
        extends JpaRepository<AgeCoefficient, Long> {

    @Query("""
            SELECT ac FROM AgeCoefficient ac
            WHERE :age BETWEEN ac.ageFrom AND ac.ageTo
            """)
    Optional<AgeCoefficient> findByAge(
            @Param("age") Integer age
    );

    @Query("""
            SELECT 1 FROM AgeCoefficient ac
            WHERE :age BETWEEN ac.ageFrom AND ac.ageTo
            """)
    boolean existsByAge(
            @Param("age") Integer age
    );

}

package org.example.proyecto.Agreement.Infrastructure;


import org.example.proyecto.Agreement.Domain.Agreement;
import org.example.proyecto.Agreement.Domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    List<Agreement> findByState(State state);
}
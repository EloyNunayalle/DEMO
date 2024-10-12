package org.example.proyecto.Agreement.Infraestructure;


import org.example.proyecto.Agreement.Domain.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Integer> {

    List<Agreement> findByStatus(Agreement.Status status);
}
package org.example.proyecto.Agreement.Infraestructure;

import org.example.proyecto.Agreement.Domain.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {
}

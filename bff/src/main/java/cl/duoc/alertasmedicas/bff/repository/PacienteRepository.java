package cl.duoc.alertasmedicas.bff.repository;

import cl.duoc.alertasmedicas.bff.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
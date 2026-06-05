package cl.duoc.alertasmedicas.bff.repository;

import cl.duoc.alertasmedicas.bff.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByEstado(String estado);
    List<Alerta> findByPacienteId(Long pacienteId);
}
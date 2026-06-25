package cl.duoc.alertasmedicas.bff.repository;

import cl.duoc.alertasmedicas.bff.model.MensajeAlertaConsumido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeAlertaConsumidoRepository extends JpaRepository<MensajeAlertaConsumido, Long> {
}
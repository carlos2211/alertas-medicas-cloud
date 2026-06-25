package cl.duoc.alertasmedicas.bff.rabbitmq;

import cl.duoc.alertasmedicas.bff.config.RabbitMQConfig;
import cl.duoc.alertasmedicas.bff.model.MensajeAlertaConsumido;
import cl.duoc.alertasmedicas.bff.repository.MensajeAlertaConsumidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertaRabbitConsumer {

    private final MensajeAlertaConsumidoRepository mensajeAlertaConsumidoRepository;

    @RabbitListener(queues = RabbitMQConfig.ALERTAS_QUEUE)
    public void consumirAlerta(Map<String, Object> alerta) {
        log.info("=========================================");
        log.info("MENSAJE RECIBIDO DESDE RABBITMQ");
        log.info("Alerta recibida: {}", alerta);
        log.info("=========================================");

        MensajeAlertaConsumido mensaje = new MensajeAlertaConsumido();

        mensaje.setAlertaId(toLong(alerta.get("id")));
        mensaje.setPacienteId(toLong(alerta.get("pacienteId")));
        mensaje.setTipo(toStringValue(alerta.get("tipo")));
        mensaje.setValorMedido(toDouble(alerta.get("valorMedido")));
        mensaje.setUnidad(toStringValue(alerta.get("unidad")));
        mensaje.setSeveridad(toStringValue(alerta.get("severidad")));
        mensaje.setEstado(toStringValue(alerta.get("estado")));
        mensaje.setMensajeOriginal(alerta.toString());

        mensajeAlertaConsumidoRepository.save(mensaje);

        log.info("Mensaje consumido guardado en Oracle Cloud con ID: {}", mensaje.getId());
    }

    private Long toLong(Object value) {
        if (value == null) return null;
        return Long.valueOf(value.toString());
    }

    private Double toDouble(Object value) {
        if (value == null) return null;
        return Double.valueOf(value.toString());
    }

    private String toStringValue(Object value) {
        return value == null ? null : value.toString();
    }
}
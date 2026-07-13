package cl.duoc.alertasmedicas.bff.kafka;

import cl.duoc.alertasmedicas.bff.config.KafkaTopicConfig;
import cl.duoc.alertasmedicas.bff.model.Alerta;
import cl.duoc.alertasmedicas.bff.repository.AlertaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertaKafkaConsumer {

    private final AlertaRepository alertaRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = KafkaTopicConfig.ALERTAS_TOPIC,
            groupId = "alertas-medicas-group"
    )
    public void consumirAlerta(String alertaJson) {
        try {
            log.info("=========================================");
            log.info("MENSAJE RECIBIDO DESDE KAFKA - ALERTA MEDICA");
            log.info("Topic: {}", KafkaTopicConfig.ALERTAS_TOPIC);
            log.info("Alerta recibida: {}", alertaJson);
            log.info("=========================================");

            Map<String, Object> data = objectMapper.readValue(alertaJson, Map.class);

            Alerta alerta = new Alerta();
            alerta.setPacienteId(toLong(data.get("pacienteId")));
            alerta.setTipo(toStringValue(data.get("tipo")));
            alerta.setValorMedido(toDouble(data.get("valorMedido")));
            alerta.setUnidad(toStringValue(data.get("unidad")));
            alerta.setSeveridad(toStringValue(data.get("severidad")));
            alerta.setEstado("ACTIVA");

            Alerta guardada = alertaRepository.save(alerta);

            log.info("Alerta recibida desde Kafka guardada en Oracle Cloud con ID: {}", guardada.getId());

        } catch (Exception e) {
            log.error("Error procesando alerta recibida desde Kafka", e);
            throw new RuntimeException("Error procesando alerta Kafka", e);
        }
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
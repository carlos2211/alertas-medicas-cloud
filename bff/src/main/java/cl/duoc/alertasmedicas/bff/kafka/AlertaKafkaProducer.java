package cl.duoc.alertasmedicas.bff.kafka;

import cl.duoc.alertasmedicas.bff.config.KafkaTopicConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertaKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void enviarAlerta(Map<String, Object> alerta) {
        try {
            String mensajeJson = objectMapper.writeValueAsString(alerta);

            log.info("Publicando alerta médica en Kafka topic {}: {}", KafkaTopicConfig.ALERTAS_TOPIC, mensajeJson);

            kafkaTemplate.send(
                    KafkaTopicConfig.ALERTAS_TOPIC,
                    mensajeJson
            );
        } catch (Exception e) {
            log.error("Error publicando alerta médica en Kafka", e);
            throw new RuntimeException("Error publicando alerta médica en Kafka", e);
        }
    }
}
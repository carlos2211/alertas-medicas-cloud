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
public class ResumenSignosKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void enviarResumen(Map<String, Object> resumen) {
        try {
            String mensajeJson = objectMapper.writeValueAsString(resumen);

            log.info("Publicando resumen de signos vitales en Kafka topic {}: {}", KafkaTopicConfig.RESUMENES_TOPIC, mensajeJson);

            kafkaTemplate.send(
                    KafkaTopicConfig.RESUMENES_TOPIC,
                    mensajeJson
            );
        } catch (Exception e) {
            log.error("Error publicando resumen de signos vitales en Kafka", e);
            throw new RuntimeException("Error publicando resumen de signos vitales en Kafka", e);
        }
    }
}
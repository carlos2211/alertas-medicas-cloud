package cl.duoc.alertasmedicas.bff.kafka;

import cl.duoc.alertasmedicas.bff.config.KafkaTopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlertaKafkaConsumer {

    @KafkaListener(
            topics = KafkaTopicConfig.ALERTAS_TOPIC,
            groupId = "alertas-medicas-group"
    )
    public void consumirAlerta(String alertaJson) {
        log.info("=========================================");
        log.info("MENSAJE RECIBIDO DESDE KAFKA - ALERTA MEDICA");
        log.info("Topic: {}", KafkaTopicConfig.ALERTAS_TOPIC);
        log.info("Alerta recibida: {}", alertaJson);
        log.info("=========================================");
    }
}
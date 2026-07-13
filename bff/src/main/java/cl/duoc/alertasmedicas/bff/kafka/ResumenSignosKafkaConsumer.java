package cl.duoc.alertasmedicas.bff.kafka;

import cl.duoc.alertasmedicas.bff.config.KafkaTopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResumenSignosKafkaConsumer {

    @KafkaListener(
            topics = KafkaTopicConfig.RESUMENES_TOPIC,
            groupId = "alertas-medicas-group"
    )
    public void consumirResumen(String resumenJson) {
        log.info("=========================================");
        log.info("MENSAJE RECIBIDO DESDE KAFKA - RESUMEN SIGNOS");
        log.info("Topic: {}", KafkaTopicConfig.RESUMENES_TOPIC);
        log.info("Resumen recibido: {}", resumenJson);
        log.info("=========================================");
    }
}
package cl.duoc.alertasmedicas.bff.rabbitmq;

import cl.duoc.alertasmedicas.bff.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumenSignosRabbitPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publicarResumenSignos(Map<String, Object> resumen) {
        log.info("Publicando resumen de signos vitales en RabbitMQ: {}", resumen);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESUMENES_EXCHANGE,
                RabbitMQConfig.RESUMENES_ROUTING_KEY,
                resumen
        );
    }
}
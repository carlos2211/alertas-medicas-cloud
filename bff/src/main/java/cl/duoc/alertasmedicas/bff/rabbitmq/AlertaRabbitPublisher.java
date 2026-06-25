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
public class AlertaRabbitPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publicarAlertaCreada(Map<String, Object> alerta) {
        log.info("Publicando alerta en RabbitMQ: {}", alerta);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ALERTAS_EXCHANGE,
                RabbitMQConfig.ALERTAS_ROUTING_KEY,
                alerta
        );
    }
}
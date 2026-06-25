package cl.duoc.alertasmedicas.bff.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ALERTAS_QUEUE = "alertas.medicas.queue";
    public static final String ALERTAS_EXCHANGE = "alertas.medicas.exchange";
    public static final String ALERTAS_ROUTING_KEY = "alerta.creada";

    @Bean
    public Queue alertasQueue() {
        return QueueBuilder.durable(ALERTAS_QUEUE).build();
    }

    @Bean
    public DirectExchange alertasExchange() {
        return new DirectExchange(ALERTAS_EXCHANGE);
    }

    @Bean
    public Binding alertasBinding() {
        return BindingBuilder
                .bind(alertasQueue())
                .to(alertasExchange())
                .with(ALERTAS_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
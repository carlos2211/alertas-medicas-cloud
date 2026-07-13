package cl.duoc.alertasmedicas.bff.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String ALERTAS_TOPIC = "alertas-medicas-topic";
    public static final String RESUMENES_TOPIC = "resumenes-signos-topic";

    @Bean
    public NewTopic alertasMedicasTopic() {
        return TopicBuilder.name(ALERTAS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic resumenesSignosTopic() {
        return TopicBuilder.name(RESUMENES_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
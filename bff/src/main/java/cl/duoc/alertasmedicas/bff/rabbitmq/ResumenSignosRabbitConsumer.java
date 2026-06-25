package cl.duoc.alertasmedicas.bff.rabbitmq;

import cl.duoc.alertasmedicas.bff.config.RabbitMQConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Service
public class ResumenSignosRabbitConsumer {

    private final ObjectMapper objectMapper;

    public ResumenSignosRabbitConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @RabbitListener(queues = RabbitMQConfig.RESUMENES_QUEUE)
    public void consumirResumenSignos(Map<String, Object> resumen) {
        try {
            log.info("=========================================");
            log.info("MENSAJE RECIBIDO DESDE RABBITMQ - RESUMEN SIGNOS");
            log.info("Resumen recibido: {}", resumen);
            log.info("=========================================");

            File carpeta = new File("/app/reportes-json");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            String fecha = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String pacienteId = resumen.get("pacienteId") != null
                    ? resumen.get("pacienteId").toString()
                    : "sin_paciente";

            File archivo = new File(
                    carpeta,
                    "resumen-signos-paciente-" + pacienteId + "-" + fecha + ".json"
            );

            objectMapper.writeValue(archivo, resumen);

            log.info("Archivo JSON generado correctamente en: {}", archivo.getAbsolutePath());

        } catch (Exception e) {
            log.error("Error al generar archivo JSON del resumen de signos vitales", e);
            throw new RuntimeException("Error al generar archivo JSON", e);
        }
    }
}
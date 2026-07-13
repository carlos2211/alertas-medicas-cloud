package cl.duoc.alertasmedicas.bff.controller;

import cl.duoc.alertasmedicas.bff.kafka.AlertaKafkaProducer;
import cl.duoc.alertasmedicas.bff.kafka.ResumenSignosKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final AlertaKafkaProducer alertaKafkaProducer;
    private final ResumenSignosKafkaProducer resumenSignosKafkaProducer;

    @PostMapping("/alertas")
    public ResponseEntity<Map<String, Object>> enviarAlertaKafka(@RequestBody Map<String, Object> dto) {
        Map<String, Object> alerta = new LinkedHashMap<>();
        alerta.put("pacienteId", dto.get("pacienteId"));
        alerta.put("tipo", dto.get("tipo"));
        alerta.put("valorMedido", dto.get("valorMedido"));
        alerta.put("unidad", dto.get("unidad"));
        alerta.put("severidad", dto.get("severidad"));
        alerta.put("fechaEvento", LocalDateTime.now().toString());

        alertaKafkaProducer.enviarAlerta(alerta);

        return ResponseEntity.status(HttpStatus.CREATED).body(alerta);
    }

    @PostMapping("/resumenes-signos")
    public ResponseEntity<Map<String, Object>> enviarResumenKafka(@RequestBody Map<String, Object> dto) {
        Map<String, Object> resumen = new LinkedHashMap<>();
        resumen.put("pacienteId", dto.get("pacienteId"));
        resumen.put("frecuenciaCardiaca", dto.get("frecuenciaCardiaca"));
        resumen.put("presionArterial", dto.get("presionArterial"));
        resumen.put("saturacionOxigeno", dto.get("saturacionOxigeno"));
        resumen.put("temperatura", dto.get("temperatura"));
        resumen.put("observacion", dto.get("observacion"));
        resumen.put("fechaEvento", LocalDateTime.now().toString());

        resumenSignosKafkaProducer.enviarResumen(resumen);

        return ResponseEntity.status(HttpStatus.CREATED).body(resumen);
    }
}
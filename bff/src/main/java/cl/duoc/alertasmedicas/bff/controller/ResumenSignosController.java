package cl.duoc.alertasmedicas.bff.controller;

import cl.duoc.alertasmedicas.bff.rabbitmq.ResumenSignosRabbitPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/resumenes-signos")
@RequiredArgsConstructor
public class ResumenSignosController {

    private final ResumenSignosRabbitPublisher resumenSignosRabbitPublisher;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearResumenSignos(
            @RequestBody Map<String, Object> dto) {

        Map<String, Object> resumen = new LinkedHashMap<>();
        resumen.put("pacienteId", dto.get("pacienteId"));
        resumen.put("frecuenciaCardiaca", dto.get("frecuenciaCardiaca"));
        resumen.put("presionArterial", dto.get("presionArterial"));
        resumen.put("saturacionOxigeno", dto.get("saturacionOxigeno"));
        resumen.put("temperatura", dto.get("temperatura"));
        resumen.put("observacion", dto.get("observacion"));
        resumen.put("fechaRegistro", LocalDateTime.now().toString());

        resumenSignosRabbitPublisher.publicarResumenSignos(resumen);

        return ResponseEntity.status(HttpStatus.CREATED).body(resumen);
    }
}
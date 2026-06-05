package cl.duoc.alertasmedicas.bff.controller;

import cl.duoc.alertasmedicas.bff.service.AlertaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarAlertas(
            @RequestParam(defaultValue = "ACTIVA") String estado) {
        return ResponseEntity.ok(alertaService.listarPorEstado(estado));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Map<String, Object>>> alertasPorPaciente(
            @PathVariable Long pacienteId) {
        return ResponseEntity.ok(alertaService.listarPorPaciente(pacienteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerAlerta(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearAlerta(
            @RequestBody Map<String, Object> alertaDTO) {
        Map<String, Object> creada = alertaService.crear(alertaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarAlerta(
            @PathVariable Long id,
            @RequestBody Map<String, Object> alertaDTO) {
        return ResponseEntity.ok(alertaService.actualizar(id, alertaDTO));
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<Map<String, Object>> resolverAlerta(@PathVariable Long id) {
        Map<String, Object> resuelta = alertaService.resolver(id, "sistema", "Sistema");
        return ResponseEntity.ok(resuelta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlerta(@PathVariable Long id) {
        alertaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
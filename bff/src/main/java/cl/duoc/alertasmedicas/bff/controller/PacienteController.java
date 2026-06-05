package cl.duoc.alertasmedicas.bff.controller;

import cl.duoc.alertasmedicas.bff.service.PacienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPaciente(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearPaciente(
            @RequestBody Map<String, Object> pacienteDTO) {
        Map<String, Object> creado = pacienteService.crear(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarPaciente(
            @PathVariable Long id,
            @RequestBody Map<String, Object> pacienteDTO) {
        return ResponseEntity.ok(pacienteService.actualizar(id, pacienteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
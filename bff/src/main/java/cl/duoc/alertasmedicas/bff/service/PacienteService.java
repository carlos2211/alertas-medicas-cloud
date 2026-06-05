package cl.duoc.alertasmedicas.bff.service;

import cl.duoc.alertasmedicas.bff.model.Paciente;
import cl.duoc.alertasmedicas.bff.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public List<Map<String, Object>> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::toMap)
                .collect(Collectors.toList());
    }

    public Map<String, Object> buscarPorId(Long id) {
        Paciente p = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado id=" + id));
        return toMap(p);
    }

    public Map<String, Object> crear(Map<String, Object> dto) {
        Paciente p = new Paciente();
        p.setNombre((String) dto.get("nombre"));
        p.setApellido((String) dto.get("apellido"));
        p.setRut((String) dto.get("rut"));
        p.setHabitacion((String) dto.get("habitacion"));
        p.setDiagnostico((String) dto.get("diagnostico"));
        if (dto.get("fechaNacimiento") != null) {
            p.setFechaNacimiento(LocalDate.parse(dto.get("fechaNacimiento").toString()));
        }
        return toMap(pacienteRepository.save(p));
    }

    public Map<String, Object> actualizar(Long id, Map<String, Object> dto) {
        Paciente p = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado id=" + id));
        if (dto.get("nombre") != null) p.setNombre((String) dto.get("nombre"));
        if (dto.get("apellido") != null) p.setApellido((String) dto.get("apellido"));
        if (dto.get("habitacion") != null) p.setHabitacion((String) dto.get("habitacion"));
        if (dto.get("diagnostico") != null) p.setDiagnostico((String) dto.get("diagnostico"));
        return toMap(pacienteRepository.save(p));
    }

    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }

    private Map<String, Object> toMap(Paciente p) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", p.getId());
        map.put("nombre", p.getNombre());
        map.put("apellido", p.getApellido());
        map.put("rut", p.getRut());
        map.put("fechaNacimiento", p.getFechaNacimiento());
        map.put("habitacion", p.getHabitacion());
        map.put("diagnostico", p.getDiagnostico());
        map.put("creadoEn", p.getCreadoEn());
        return map;
    }
}
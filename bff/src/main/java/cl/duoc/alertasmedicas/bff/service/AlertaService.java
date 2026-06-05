package cl.duoc.alertasmedicas.bff.service;

import cl.duoc.alertasmedicas.bff.model.Alerta;
import cl.duoc.alertasmedicas.bff.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public List<Map<String, Object>> listarPorEstado(String estado) {
        return alertaRepository.findByEstado(estado)
                .stream()
                .map(this::toMap)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> listarPorPaciente(Long pacienteId) {
        return alertaRepository.findByPacienteId(pacienteId)
                .stream()
                .map(this::toMap)
                .collect(Collectors.toList());
    }

    public Map<String, Object> buscarPorId(Long id) {
        Alerta a = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada id=" + id));
        return toMap(a);
    }

    public Map<String, Object> crear(Map<String, Object> dto) {
        Alerta a = new Alerta();
        a.setPacienteId(Long.valueOf(dto.get("pacienteId").toString()));
        a.setTipo((String) dto.get("tipo"));
        a.setValorMedido(Double.valueOf(dto.get("valorMedido").toString()));
        a.setUnidad((String) dto.get("unidad"));
        a.setSeveridad((String) dto.get("severidad"));
        a.setEstado("ACTIVA");
        return toMap(alertaRepository.save(a));
    }

    public Map<String, Object> actualizar(Long id, Map<String, Object> dto) {
        Alerta a = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada id=" + id));
        if (dto.get("estado") != null) a.setEstado((String) dto.get("estado"));
        if (dto.get("severidad") != null) a.setSeveridad((String) dto.get("severidad"));
        return toMap(alertaRepository.save(a));
    }

    public Map<String, Object> resolver(Long id, String subMedico, String nombreMedico) {
        Alerta a = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada id=" + id));
        a.setEstado("RESUELTA");
        a.setResueltaPor(subMedico);
        a.setResueltaEn(LocalDateTime.now());
        return toMap(alertaRepository.save(a));
    }

    public void eliminar(Long id) {
        alertaRepository.deleteById(id);
    }

    private Map<String, Object> toMap(Alerta a) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", a.getId());
        map.put("pacienteId", a.getPacienteId());
        map.put("tipo", a.getTipo());
        map.put("valorMedido", a.getValorMedido());
        map.put("unidad", a.getUnidad());
        map.put("severidad", a.getSeveridad());
        map.put("estado", a.getEstado());
        map.put("creadaEn", a.getCreadaEn());
        map.put("resueltaEn", a.getResueltaEn());
        map.put("resueltaPor", a.getResueltaPor());
        return map;
    }
}
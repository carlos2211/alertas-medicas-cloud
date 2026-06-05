package cl.duoc.alertasmedicas.bff.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> obtenerUsuarioActual() {
        Map<String, Object> usuario = new LinkedHashMap<>();
        usuario.put("mensaje", "Autenticación via Azure AD B2C configurada");
        usuario.put("estado", "OK");
        return ResponseEntity.ok(usuario);
    }

    @PostMapping(value = "/token-callback", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> tokenCallback(@RequestParam Map<String, String> params) {
        String idToken = params.get("id_token");
        log.info("=== ID TOKEN RECIBIDO ===");
        log.info(idToken);
        return ResponseEntity.ok(idToken);
    }
}
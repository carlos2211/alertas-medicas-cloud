package cl.duoc.alertasmedicas.bff.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones.
 * Retorna respuestas RFC 7807 (Problem Detail) en formato JSON.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Token JWT inválido o expirado */
    @ExceptionHandler(JwtException.class)
    public ProblemDetail handleJwtException(JwtException ex) {
        log.warn("JWT inválido: {}", ex.getMessage());
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pd.setTitle("Token inválido");
        pd.setDetail(ex.getMessage());
        pd.setType(URI.create("/errors/jwt-invalid"));
        return pd;
    }

    /** Sin permiso para el recurso (roles insuficientes) */
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException ex) {
        log.warn("Acceso denegado: {}", ex.getMessage());
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        pd.setTitle("Acceso denegado");
        pd.setDetail("No tienes permisos para realizar esta acción");
        pd.setType(URI.create("/errors/access-denied"));
        return pd;
    }

    /** Errores de validación (@Valid) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(
                fe -> fe.getField(),
                fe -> fe.getDefaultMessage(),
                (a, b) -> a
            ));
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Error de validación");
        pd.setDetail("Uno o más campos son inválidos");
        pd.setProperty("errores", errors);
        pd.setProperty("timestamp", LocalDateTime.now().toString());
        return pd;
    }

    /** Error genérico */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex) {
        log.error("Error no manejado", ex);
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setTitle("Error interno");
        pd.setDetail("Ocurrió un error inesperado");
        return pd;
    }
}

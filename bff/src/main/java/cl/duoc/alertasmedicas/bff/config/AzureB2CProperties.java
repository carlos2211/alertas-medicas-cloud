package cl.duoc.alertasmedicas.bff.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Mapea las propiedades azure.b2c.* del application.yml.
 * Úsalas inyectando este bean donde necesites los datos del tenant.
 */
@Data
@Component
@ConfigurationProperties(prefix = "azure.b2c")
public class AzureB2CProperties {

    /** Nombre del tenant, ej: miempresa → miempresa.b2clogin.com */
    private String tenantName;

    /** GUID del tenant Azure AD B2C */
    private String tenantId;

    /** Client ID de la App Registration en Azure (para validar claim aud) */
    private String clientId;

    /** Nombre del User Flow, ej: B2C_1_signupsignin */
    private String flowName;

    /**
     * Retorna el issuer URI completo del flujo de usuario.
     * Este valor debe coincidir con la claim "iss" del JWT.
     */
    public String getIssuerUri() {
        return String.format(
            "https://%s.b2clogin.com/%s.onmicrosoft.com/v2.0/",
            tenantName, tenantName
        );
    }

    /**
     * JWKS URI donde Azure publica sus claves públicas para verificar firmas JWT.
     */
    public String getJwkSetUri() {
        return String.format(
            "https://%s.b2clogin.com/%s.onmicrosoft.com/discovery/v2.0/keys?p=%s",
            tenantName, tenantName, flowName
        );
    }
}

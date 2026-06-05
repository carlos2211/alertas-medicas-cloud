# 🏥 Sistema de Alertas Médicas en Tiempo Real
**DSY2206 – Desarrollo Cloud Native I | Experiencia 1**

Backend For Frontend (BFF) con Spring Boot integrado a **Azure AD B2C (IDaaS)**.

---

## 🏗️ Arquitectura

```
Angular (MSAL) ──► Azure AD B2C ──► JWT
                                      │
                                      ▼
                              BFF (Spring Boot)
                              Spring Security
                              OAuth2 Resource Server
                                      │
                          ┌───────────┴───────────┐
                          ▼                       ▼
                   Oracle Database         API Manager
                   (pacientes, alertas)    (exposición endpoints)
```

---

## ⚙️ Configuración de Azure AD B2C

### 1. App Registration en Azure Portal

1. Ir a **Azure Portal → Azure AD B2C → App registrations → New registration**
2. Nombre: `alertas-medicas-bff`
3. Tipo de cuenta: **Accounts in any identity provider**
4. URI de redirección: `http://localhost:4200` (para Angular local)
5. Copiar el **Application (client) ID** → `AZURE_B2C_CLIENT_ID`
6. Copiar el **Directory (tenant) ID** → `AZURE_B2C_TENANT_ID`

### 2. Exponer una API (Scopes)

En la App Registration → **Expose an API**:
1. Set Application ID URI: `api://<client-id>`
2. Add a scope: `access_as_user`

### 3. App Roles (para @PreAuthorize)

En la App Registration → **App roles → Create app role**:
- `MEDICO` – para médicos
- `ENFERMERO` – para enfermeros
- `ADMIN` – para administradores del sistema

### 4. User Flow en Azure AD B2C

1. Ir a **Azure AD B2C → User flows → New user flow**
2. Tipo: **Sign up and sign in** (versión Recommended)
3. Nombre: `B2C_1_signupsignin` → `AZURE_B2C_FLOW_NAME`
4. En **Application claims**, marcar: `Display Name`, `Email Addresses`, `Given Name`, `Surname`

---

## 🔐 Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto (**no lo subas a Git**):

```env
AZURE_B2C_TENANT_NAME=miempresa
AZURE_B2C_TENANT_ID=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
AZURE_B2C_CLIENT_ID=yyyyyyyy-yyyy-yyyy-yyyy-yyyyyyyyyyyy
AZURE_B2C_FLOW_NAME=B2C_1_signupsignin
ORACLE_PASSWORD=AlertasPass123
```

---

## 🚀 Ejecutar con Docker

```bash
# 1. Clona el repositorio
git clone <url-repo>
cd alertas-medicas

# 2. Crea el .env con tus valores reales de Azure
cp .env.example .env
# Edita .env con tus datos

# 3. Levanta todos los servicios
docker compose up --build

# 4. Verifica que el BFF esté corriendo
curl http://localhost:8080/actuator/health
```

---

## 📋 Endpoints del BFF

### Auth
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/auth/me` | Info del usuario autenticado (desde JWT) |

### Pacientes
| Método | Endpoint | Roles permitidos |
|--------|----------|-----------------|
| GET | `/api/v1/pacientes` | MEDICO, ENFERMERO, ADMIN |
| GET | `/api/v1/pacientes/{id}` | MEDICO, ENFERMERO, ADMIN |
| POST | `/api/v1/pacientes` | MEDICO, ADMIN |
| PUT | `/api/v1/pacientes/{id}` | MEDICO, ADMIN |
| DELETE | `/api/v1/pacientes/{id}` | ADMIN |

### Alertas
| Método | Endpoint | Roles permitidos |
|--------|----------|-----------------|
| GET | `/api/v1/alertas?estado=ACTIVA` | MEDICO, ENFERMERO, ADMIN |
| GET | `/api/v1/alertas/{id}` | MEDICO, ENFERMERO, ADMIN |
| GET | `/api/v1/alertas/paciente/{id}` | MEDICO, ENFERMERO, ADMIN |
| POST | `/api/v1/alertas` | MEDICO, ENFERMERO, ADMIN, SISTEMA |
| PUT | `/api/v1/alertas/{id}` | MEDICO, ENFERMERO, ADMIN |
| PUT | `/api/v1/alertas/{id}/resolver` | MEDICO, ADMIN |
| DELETE | `/api/v1/alertas/{id}` | ADMIN |

---

## 🧪 Pruebas con POSTMAN

### Paso 1: Obtener el JWT desde Azure AD B2C
Usa el flujo OAuth2 Authorization Code en Postman:
- **Auth URL**: `https://<tenant>.b2clogin.com/<tenant>.onmicrosoft.com/oauth2/v2.0/authorize?p=B2C_1_signupsignin`
- **Token URL**: `https://<tenant>.b2clogin.com/<tenant>.onmicrosoft.com/oauth2/v2.0/token?p=B2C_1_signupsignin`
- **Client ID**: `<AZURE_B2C_CLIENT_ID>`
- **Scope**: `openid profile offline_access`

### Paso 2: Usar el token en las peticiones
En Postman → Authorization → Bearer Token → pega el JWT obtenido.

### Ejemplo: Crear una alerta
```json
POST http://localhost:8080/api/v1/alertas
Authorization: Bearer <jwt>
Content-Type: application/json

{
  "pacienteId": 1,
  "tipo": "FRECUENCIA_CARDIACA",
  "valorMedido": 145,
  "unidad": "bpm",
  "severidad": "ALTA"
}
```

---

## 📁 Estructura del Proyecto

```
alertas-medicas/
├── bff/
│   ├── src/main/java/cl/duoc/alertasmedicas/bff/
│   │   ├── BffApplication.java
│   │   ├── config/
│   │   │   ├── SecurityConfig.java        ← Spring Security + Azure AD B2C
│   │   │   ├── AzureB2CProperties.java    ← Propiedades del tenant
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── controller/
│   │   │   ├── AuthController.java        ← GET /auth/me
│   │   │   ├── PacienteController.java    ← CRUD pacientes
│   │   │   └── AlertaController.java      ← CRUD alertas
│   │   ├── service/
│   │   │   ├── PacienteService.java
│   │   │   └── AlertaService.java
│   │   └── dto/
│   │       └── AlertasMedicasDTO.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   └── pom.xml
├── sql/
│   └── init.sql                           ← DDL Oracle (tablas + datos prueba)
├── docker-compose.yml
├── .env.example
└── README.md
```

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21">
  <img src="https://img.shields.io/badge/Jakarta_EE-5.0-0066CC?style=for-the-badge&logo=eclipsejakartaee&logoColor=white" alt="Jakarta EE 5.0">
  <img src="https://img.shields.io/badge/PostgreSQL-42.7-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/Maven-3-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/Tomcat-10-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black" alt="Tomcat 10">
</p>

<h1 align="center">PlayLog</h1>

<p align="center">
  <b>Tu biblioteca gaming personal — Descubre, trackea y comparte tu experiencia gaming con la comunidad.</b>
</p>

<p align="center">
  PlayLog es una aplicación web que permite a los gamers organizar su colección de videojuegos, escribir reseñas, votar las opiniones de otros usuarios y descubrir nuevos títulos a través de la <a href="https://rawg.io/apidocs">API de RAWG</a>.
</p>

<p align="center">
  <a href="https://www.playlog.codes/"><b>www.playlog.codes</b></a>
</p>

---

## Funcionalidades

### Búsqueda de Juegos
- Busca entre **más de 800,000 videojuegos** gracias a la integración con la API de RAWG.
- Visualiza información detallada: nombre, imagen, puntuación Metacritic, fecha de lanzamiento, géneros y plataformas.

### Biblioteca Personal
- Agrega juegos a tu biblioteca y clasifícalos por estado:
  - **Jugando** — Lo estás jugando actualmente
  - **Completado** — Ya lo terminaste
  - **Quiero Jugar** — En tu lista de deseos
  - **Abandonado** — Lo dejaste de lado
- Califica cada juego (1-10) y selecciona en qué plataforma lo jugaste.
- Escribe reseñas para compartir tu opinión con la comunidad.

### Comunidad
- Consulta estadísticas globales: juegos **más deseados**, **mejor votados** y **más completados**.
- Lee las últimas reseñas publicadas por otros usuarios.
- Vota las reseñas con 👍 o 👎 para destacar las mejores opiniones.

### Perfiles de Usuario
- Cada usuario tiene un perfil público con sus estadísticas y biblioteca.
- Sistema de **seguidores**: sigue a otros jugadores para ver su actividad.

### Autenticación Segura
- Registro e inicio de sesión con email y contraseña.
- Contraseñas hasheadas con **BCrypt** (factor 12).
- Sesiones manejadas con **JSON Web Tokens (JWT)** almacenados en cookies HTTP-only.
- Filtro de autenticación global que protege todas las rutas excepto login y registro.

---

## Arquitectura

El proyecto sigue una **arquitectura MVC (Modelo-Vista-Controlador)** con capas bien definidas:

```
com.tracker
├── controlador/    # Servlets (Controladores HTTP)
├── dao/            # Data Access Objects (Acceso a Base de Datos)
├── modelo/         # JavaBeans / Entidades (POJOs)
└── servicio/       # Lógica de Negocio (Servicios)
```

### Capas

| Capa | Responsabilidad | Ejemplos |
|------|----------------|----------|
| **Controlador** | Recibe peticiones HTTP, delega al servicio y redirige a la vista | `LoginServlet`, `BibliotecaServlet`, `BuscarServlet` |
| **Servicio** | Lógica de negocio, autenticación, consumo de APIs | `AuthService`, `RawgService`, `EstadisticasService` |
| **DAO** | Consultas SQL y acceso a PostgreSQL | `UsuarioDAO`, `JuegoDAO`, `UsuarioJuegoDAO` |
| **Modelo** | Entidades del dominio (JavaBeans serializables) | `Usuario`, `Juego`, `UsuarioJuego`, `Voto` |
| **Vista** | Páginas JSP con JSTL para renderizado del lado del servidor | `inicio.jsp`, `biblioteca.jsp`, `comunidad.jsp` |

---

## Tech Stack

| Categoría | Tecnología |
|-----------|-----------|
| **Lenguaje** | Java 21 |
| **Servidor** | Apache Tomcat 10+ (Jakarta Servlet 5.0) |
| **Build** | Apache Maven |
| **Base de Datos** | PostgreSQL |
| **Vistas** | JSP + JSTL (Jakarta) |
| **Autenticación** | JWT (JJWT 0.11.5) + BCrypt |
| **API Externa** | [RAWG Video Games Database API](https://rawg.io/apidocs) |
| **HTTP Client** | `java.net.http.HttpClient` (Java 21) |
| **JSON Parsing** | Gson 2.10.1 |
| **Frontend** | HTML5, CSS3 (Glassmorphism), JavaScript, Font Awesome, Google Fonts (Inter) |

---

## Estructura del Proyecto

```
PlayLog/
├── pom.xml                          # Configuración Maven y dependencias
├── .env.example                     # Plantilla de variables de entorno
├── .gitignore
│
└── src/main/
    ├── java/com/tracker/
    │   ├── controlador/             # Servlets (Login, Registro, Búsqueda, etc.)
    │   ├── dao/                     # DAOs (Conexión, Usuarios, Juegos, Votos, etc.)
    │   ├── modelo/                  # Entidades (Usuario, Juego, Voto, etc.)
    │   └── servicio/                # Servicios (Auth, RAWG, Estadísticas)
    │
    ├── resources/
    │
    └── webapp/
        ├── WEB-INF/
        │   └── web.xml              # Configuración del Servlet Container
        ├── recursos/
        │   ├── css/estilo.css       # Estilos globales (Glassmorphism UI)
        │   └── js/app.js            # JavaScript del frontend
        ├── inicio.jsp               # Página principal (feed de actividad)
        ├── login.jsp                # Inicio de sesión
        ├── registro.jsp             # Registro de usuario
        ├── buscar.jsp               # Búsqueda de juegos (RAWG API)
        ├── juego_detalle.jsp        # Detalle de un juego
        ├── biblioteca.jsp           # Biblioteca personal del usuario
        ├── comunidad.jsp            # Estadísticas y rankings globales
        └── perfil.jsp               # Perfil de usuario
```

---

## Endpoints Principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/login` | Formulario de inicio de sesión |
| `POST` | `/login` | Autenticar usuario |
| `GET/POST` | `/registro` | Registro de nuevo usuario |
| `GET` | `/logout` | Cerrar sesión |
| `GET` | `/inicio` | Página principal con rankings y reseñas |
| `GET` | `/buscar?q=...` | Buscar juegos en RAWG |
| `GET` | `/juego?apiId=...` | Detalle de un juego |
| `GET` | `/biblioteca` | Biblioteca personal |
| `POST` | `/agregar-juego` | Agregar juego a la biblioteca |
| `POST` | `/actualizar-juego` | Actualizar estado/calificación/reseña |
| `POST` | `/eliminar-juego` | Eliminar juego de la biblioteca |
| `GET` | `/comunidad` | Estadísticas de la comunidad |
| `GET` | `/perfil?id=...` | Perfil de un usuario |
| `POST` | `/seguimiento` | Seguir/dejar de seguir usuario |
| `POST` | `/voto` | Votar una reseña (👍/👎) |

---

## Seguridad

- **JWT** con expiración de 24 horas, firmados con HMAC-SHA256
- **BCrypt** con factor de coste 12 para el hasheo de contraseñas
- Tokens almacenados en **cookies HTTP-only**
- **AuthFilter** global que intercepta todas las peticiones y valida el token
- Las credenciales sensibles se manejan mediante **variables de entorno** (nunca hardcodeadas)
- Codificación **UTF-8** en request y response para prevenir problemas de charset

---

## Licencia

Este proyecto fue desarrollado con fines académicos.

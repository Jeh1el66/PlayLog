<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="PlayLog — Descubre, trackea y comparte tus videojuegos favoritos">
    <title>PlayLog — Inicio</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/estilo.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
</head>
<body>
    <!--navegacion-->
    <nav class="navbar glass-nav">
        <div class="nav-container">
            <a href="${pageContext.request.contextPath}/inicio" class="nav-logo" id="navLogo">
                <i class="fas fa-gamepad"></i> PlayLog
            </a>
            <div class="nav-search">
                <form action="${pageContext.request.contextPath}/buscar" method="get" id="searchForm">
                    <div class="search-box">
                        <i class="fas fa-search"></i>
                        <input type="text" name="q" placeholder="Buscar juegos..." id="searchInput">
                    </div>
                </form>
            </div>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/inicio" class="nav-link active" id="navInicio">
                    <i class="fas fa-home"></i> Inicio
                </a>
                <a href="${pageContext.request.contextPath}/biblioteca" class="nav-link" id="navBiblioteca">
                    <i class="fas fa-book"></i> Biblioteca
                </a>
                <a href="${pageContext.request.contextPath}/comunidad" class="nav-link" id="navComunidad">
                    <i class="fas fa-users"></i> Comunidad
                </a>
                <a href="${pageContext.request.contextPath}/perfil" class="nav-link" id="navPerfil">
                    <i class="fas fa-user"></i> ${nombreUsuario}
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="nav-link nav-logout" id="navLogout">
                    <i class="fas fa-sign-out-alt"></i>
                </a>
            </div>
        </div>
    </nav>

    <main class="main-content">
        <!--seccion principal-->
        <section class="hero-section">
            <div class="hero-content">
                <h1>Bienvenido a <span class="gradient-text">PlayLog</span></h1>
                <p>Descubre, trackea y comparte tu experiencia gaming con la comunidad</p>
            </div>
        </section>

        <c:if test="${not empty error}">
            <div class="container">
                <div class="alert alert-error">
                    <i class="fas fa-exclamation-circle"></i>
                    <span>${error}</span>
                </div>
            </div>
        </c:if>

        <!--juegos mas deseados -->
        <section class="game-section">
            <div class="container">
                <h2 class="section-title">
                    <i class="fas fa-fire"></i> Más Deseados
                </h2>
                <div class="game-grid">
                    <c:forEach var="juego" items="${masDeseados}">
                        <a href="${pageContext.request.contextPath}/juego?apiId=${juego.apiId}" class="game-card glass-card" id="deseado-${juego.idJuego}">
                            <div class="game-card-img">
                                <c:choose>
                                    <c:when test="${not empty juego.imgUrl}">
                                        <img src="${juego.imgUrl}" alt="${juego.nombre}" loading="lazy">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-img"><i class="fas fa-gamepad"></i></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="game-card-info">
                                <h3>${juego.nombre}</h3>
                                <c:if test="${juego.metacritic != null}">
                                    <span class="metacritic-badge">${juego.metacritic}</span>
                                </c:if>
                            </div>
                        </a>
                    </c:forEach>
                    <c:if test="${empty masDeseados}">
                        <p class="empty-message">Aún no hay juegos deseados. ¡Sé el primero en agregar!</p>
                    </c:if>
                </div>
            </div>
        </section>

        <!--mejor votados-->
        <section class="game-section">
            <div class="container">
                <h2 class="section-title">
                    <i class="fas fa-trophy"></i> Mejor Votados
                </h2>
                <div class="game-grid">
                    <c:forEach var="juego" items="${mejorVotados}">
                        <a href="${pageContext.request.contextPath}/juego?apiId=${juego.apiId}" class="game-card glass-card" id="votado-${juego.idJuego}">
                            <div class="game-card-img">
                                <c:choose>
                                    <c:when test="${not empty juego.imgUrl}">
                                        <img src="${juego.imgUrl}" alt="${juego.nombre}" loading="lazy">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-img"><i class="fas fa-gamepad"></i></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="game-card-info">
                                <h3>${juego.nombre}</h3>
                                <c:if test="${juego.metacritic != null}">
                                    <span class="metacritic-badge">${juego.metacritic}</span>
                                </c:if>
                            </div>
                        </a>
                    </c:forEach>
                    <c:if test="${empty mejorVotados}">
                        <p class="empty-message">Aún no hay votos registrados.</p>
                    </c:if>
                </div>
            </div>
        </section>

        <!--mas Completados -->
        <section class="game-section">
            <div class="container">
                <h2 class="section-title">
                    <i class="fas fa-check-circle"></i> Más Completados
                </h2>
                <div class="game-grid">
                    <c:forEach var="juego" items="${masCompletados}">
                        <a href="${pageContext.request.contextPath}/juego?apiId=${juego.apiId}" class="game-card glass-card" id="completado-${juego.idJuego}">
                            <div class="game-card-img">
                                <c:choose>
                                    <c:when test="${not empty juego.imgUrl}">
                                        <img src="${juego.imgUrl}" alt="${juego.nombre}" loading="lazy">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-img"><i class="fas fa-gamepad"></i></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="game-card-info">
                                <h3>${juego.nombre}</h3>
                                <c:if test="${juego.metacritic != null}">
                                    <span class="metacritic-badge">${juego.metacritic}</span>
                                </c:if>
                            </div>
                        </a>
                    </c:forEach>
                    <c:if test="${empty masCompletados}">
                        <p class="empty-message">Aún no hay juegos completados.</p>
                    </c:if>
                </div>
            </div>
        </section>
    </main>

    <footer class="footer glass-nav">
        <div class="container">
            <p>&copy; 2026 PlayLog — Tu biblioteca gaming personal</p>
        </div>
    </footer>

    <script src="${pageContext.request.contextPath}/recursos/js/app.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Busca juegos en PlayLog">
    <title>PlayLog — Buscar</title>
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
                        <input type="text" name="q" placeholder="Buscar juegos..." value="${query}" id="searchInput">
                    </div>
                </form>
            </div>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/inicio" class="nav-link" id="navInicio">
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
        <div class="container">
            <h1 class="page-title">
                <i class="fas fa-search"></i> Resultados de búsqueda
                <c:if test="${not empty query}">
                    <span class="query-text">para "${query}"</span>
                </c:if>
            </h1>

            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <i class="fas fa-exclamation-circle"></i>
                    <span>${error}</span>
                </div>
            </c:if>

            <c:if test="${not empty mensaje}">
                <div class="alert alert-info">
                    <i class="fas fa-info-circle"></i>
                    <span>${mensaje}</span>
                </div>
            </c:if>

            <c:if test="${fuenteRawg}">
                <div class="alert alert-info">
                    <i class="fas fa-globe"></i>
                    <span>Resultados obtenidos desde RAWG. Haz clic en un juego para agregarlo a tu biblioteca.</span>
                </div>
            </c:if>

            <div class="game-grid">
                <c:forEach var="juego" items="${resultados}">
                    <a href="${pageContext.request.contextPath}/juego?apiId=${not empty juego.apiId ? juego.apiId : juego.idJuego}"
                       class="game-card glass-card" id="resultado-${juego.apiId}">
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
                            <div class="game-card-meta">
                                <c:if test="${juego.metacritic != null}">
                                    <span class="metacritic-badge">${juego.metacritic}</span>
                                </c:if>
                                <c:if test="${juego.fechaLanzamiento != null}">
                                    <span class="release-date">
                                        <i class="fas fa-calendar"></i> ${juego.fechaLanzamiento}
                                    </span>
                                </c:if>
                            </div>
                            <c:if test="${not empty juego.generos}">
                                <div class="game-genres">
                                    <c:forEach var="genero" items="${juego.generos}" varStatus="st">
                                        <span class="genre-tag">${genero.nombre}</span>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </div>
                    </a>
                </c:forEach>
            </div>

            <c:if test="${empty resultados && not empty query}">
                <div class="empty-state">
                    <i class="fas fa-search fa-3x"></i>
                    <h2>No se encontraron resultados</h2>
                    <p>Intenta con otra búsqueda</p>
                </div>
            </c:if>

            <c:if test="${empty query}">
                <div class="empty-state">
                    <i class="fas fa-search fa-3x"></i>
                    <h2>Busca tu próximo juego</h2>
                    <p>Ingresa el nombre de un juego en la barra de búsqueda</p>
                </div>
            </c:if>
        </div>
    </main>

    <footer class="footer glass-nav">
        <div class="container">
            <p>&copy; 2026 PlayLog — Tu biblioteca gaming personal</p>
        </div>
    </footer>

    <script src="${pageContext.request.contextPath}/recursos/js/app.js"></script>
</body>
</html>

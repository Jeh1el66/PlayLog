<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Detalle del juego en PlayLog">
    <title>PlayLog — ${not empty juego ? juego.nombre : 'Detalle'}</title>
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
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <i class="fas fa-exclamation-circle"></i>
                    <span>${error}</span>
                </div>
            </c:if>

            <c:if test="${not empty juego}">
                <div class="game-detail glass-card">
                    <div class="game-detail-header">
                        <div class="game-detail-img">
                            <c:choose>
                                <c:when test="${not empty juego.imgUrl}">
                                    <img src="${juego.imgUrl}" alt="${juego.nombre}">
                                </c:when>
                                <c:otherwise>
                                    <div class="no-img large"><i class="fas fa-gamepad"></i></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="game-detail-info">
                            <h1>${juego.nombre}</h1>

                            <div class="game-detail-meta">
                                <c:if test="${juego.metacritic != null}">
                                    <div class="detail-meta-item">
                                        <span class="metacritic-badge large">${juego.metacritic}</span>
                                        <span>Metacritic</span>
                                    </div>
                                </c:if>
                                <c:if test="${juego.fechaLanzamiento != null}">
                                    <div class="detail-meta-item">
                                        <i class="fas fa-calendar-alt"></i>
                                        <span>${juego.fechaLanzamiento}</span>
                                    </div>
                                </c:if>
                            </div>

                            <c:if test="${not empty juego.generos}">
                                <div class="game-genres">
                                    <c:forEach var="genero" items="${juego.generos}">
                                        <span class="genre-tag">${genero.nombre}</span>
                                    </c:forEach>
                                </div>
                            </c:if>

                            <c:if test="${not empty juego.plataformas}">
                                <div class="game-platforms">
                                    <h3><i class="fas fa-desktop"></i> Plataformas</h3>
                                    <div class="platform-list">
                                        <c:forEach var="plat" items="${juego.plataformas}">
                                            <span class="platform-tag">${plat.nombre}</span>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <!--formulario para agregar a biblioteca-->
                    <div class="add-to-library glass-card">
                        <h3><i class="fas fa-plus-circle"></i> Agregar a mi Biblioteca</h3>
                        <form action="${pageContext.request.contextPath}/agregar-juego" method="post" class="add-form" id="addGameForm">
                            <input type="hidden" name="idJuego" value="${juego.idJuego}">

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="estado">Estado</label>
                                    <select name="estado" id="estado" required>
                                        <option value="QUIERO_JUGAR">Quiero Jugar</option>
                                        <option value="JUGANDO">Jugando</option>
                                        <option value="COMPLETADO">Completado</option>
                                        <option value="ABANDONADO">Abandonado</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="idPlataforma">Plataforma</label>
                                    <select name="idPlataforma" id="idPlataforma">
                                        <option value="">-- Seleccionar --</option>
                                        <c:forEach var="plat" items="${todasPlataformas}">
                                            <option value="${plat.idPlataforma}">${plat.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <button type="submit" class="btn btn-primary" id="btnAgregar">
                                    <i class="fas fa-plus"></i> Agregar
                                </button>
                            </div>
                        </form>
                    </div>
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

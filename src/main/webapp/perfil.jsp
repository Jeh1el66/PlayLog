<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Perfil de usuario en PlayLog">
    <title>PlayLog — ${not empty perfilUsuario ? perfilUsuario.nombre : 'Perfil'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/estilo.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
</head>
<body>
    <!--nav-->
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
                <a href="${pageContext.request.contextPath}/perfil" class="nav-link active" id="navPerfil">
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

            <c:if test="${not empty perfilUsuario}">
                <!-- Cabecera del perfil -->
                <div class="profile-header glass-card">
                    <div class="profile-avatar">
                        <i class="fas fa-user-circle"></i>
                    </div>
                    <div class="profile-info">
                        <h1>${perfilUsuario.nombre}</h1>
                        <p class="profile-email">${perfilUsuario.email}</p>
                        <p class="profile-date">
                            <i class="fas fa-calendar"></i> Miembro desde ${perfilUsuario.fechaRegistro}
                        </p>

                        <div class="profile-social">
                            <span><i class="fas fa-users"></i> ${seguidores.size()} seguidores</span>
                            <span><i class="fas fa-user-friends"></i> ${seguidos.size()} siguiendo</span>
                        </div>

                        <c:if test="${!esMiPerfil}">
                            <form action="${pageContext.request.contextPath}/seguimiento" method="post" class="inline-form">
                                <input type="hidden" name="idSeguido" value="${perfilUsuario.idUsuario}">
                                <c:choose>
                                    <c:when test="${yaSigue}">
                                        <input type="hidden" name="accion" value="dejarDeSeguir">
                                        <button type="submit" class="btn btn-secondary" id="btnDejarSeguir">
                                            <i class="fas fa-user-minus"></i> Dejar de seguir
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="accion" value="seguir">
                                        <button type="submit" class="btn btn-primary" id="btnSeguir">
                                            <i class="fas fa-user-plus"></i> Seguir
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </c:if>
                    </div>
                </div>

                <!--estadisticas-->
                <div class="stats-grid">
                    <div class="stat-card glass-card">
                        <div class="stat-icon"><i class="fas fa-gamepad"></i></div>
                        <div class="stat-value">${stats.getOrDefault('TOTAL', 0)}</div>
                        <div class="stat-label">Total Juegos</div>
                    </div>
                    <div class="stat-card glass-card">
                        <div class="stat-icon playing"><i class="fas fa-play"></i></div>
                        <div class="stat-value">${stats.getOrDefault('JUGANDO', 0)}</div>
                        <div class="stat-label">Jugando</div>
                    </div>
                    <div class="stat-card glass-card">
                        <div class="stat-icon completed"><i class="fas fa-check"></i></div>
                        <div class="stat-value">${stats.getOrDefault('COMPLETADO', 0)}</div>
                        <div class="stat-label">Completados</div>
                    </div>
                    <div class="stat-card glass-card">
                        <div class="stat-icon wishlist"><i class="fas fa-star"></i></div>
                        <div class="stat-value">${stats.getOrDefault('QUIERO_JUGAR', 0)}</div>
                        <div class="stat-label">Quiero Jugar</div>
                    </div>
                    <div class="stat-card glass-card">
                        <div class="stat-icon dropped"><i class="fas fa-times"></i></div>
                        <div class="stat-value">${stats.getOrDefault('ABANDONADO', 0)}</div>
                        <div class="stat-label">Abandonados</div>
                    </div>
                </div>

                <!--bibliotrca publica-->
                <section class="game-section">
                    <h2 class="section-title">
                        <i class="fas fa-book"></i> Biblioteca
                    </h2>
                    <div class="game-grid">
                        <c:forEach var="uj" items="${biblioteca}">
                            <div class="game-card glass-card" id="profGame-${uj.idUsuarioJuego}">
                                <div class="game-card-img">
                                    <c:choose>
                                        <c:when test="${not empty uj.juego.imgUrl}">
                                            <img src="${uj.juego.imgUrl}" alt="${uj.juego.nombre}" loading="lazy">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="no-img"><i class="fas fa-gamepad"></i></div>
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="status-badge status-${uj.estado} overlay-badge">
                                        <c:choose>
                                            <c:when test="${uj.estado == 'JUGANDO'}">Jugando</c:when>
                                            <c:when test="${uj.estado == 'COMPLETADO'}">Completado</c:when>
                                            <c:when test="${uj.estado == 'QUIERO_JUGAR'}">Quiero Jugar</c:when>
                                            <c:when test="${uj.estado == 'ABANDONADO'}">Abandonado</c:when>
                                        </c:choose>
                                    </span>
                                </div>
                                <div class="game-card-info">
                                    <h3>${uj.juego.nombre}</h3>
                                    <c:if test="${uj.calificacion != null}">
                                        <span class="rating"><i class="fas fa-star"></i> ${uj.calificacion}/10</span>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <c:if test="${empty biblioteca}">
                        <div class="empty-state small">
                            <p>Esta biblioteca está vacía.</p>
                        </div>
                    </c:if>
                </section>
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

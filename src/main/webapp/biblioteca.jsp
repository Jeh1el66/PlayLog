<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Tu biblioteca de juegos en PlayLog">
    <title>PlayLog — Mi Biblioteca</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/estilo.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
</head>
<body>
    <!--Navegacion-->
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
                <a href="${pageContext.request.contextPath}/biblioteca" class="nav-link active" id="navBiblioteca">
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
                <i class="fas fa-book"></i> Mi Biblioteca
            </h1>

            <c:if test="${not empty param.error}">
                <div class="alert alert-error">
                    <i class="fas fa-exclamation-circle"></i>
                    <span>${param.error}</span>
                </div>
            </c:if>

            <!--filtros por estado-->
            <div class="filter-bar glass-card">
                <a href="${pageContext.request.contextPath}/biblioteca"
                   class="filter-btn ${empty estadoFiltro ? 'active' : ''}" id="filtroTodos">
                    <i class="fas fa-list"></i> Todos
                </a>
                <c:forEach var="est" items="${estados}">
                    <a href="${pageContext.request.contextPath}/biblioteca?estado=${est}"
                       class="filter-btn ${estadoFiltro == est.name() ? 'active' : ''}" id="filtro-${est}">
                        <c:choose>
                            <c:when test="${est == 'JUGANDO'}"><i class="fas fa-play"></i> Jugando</c:when>
                            <c:when test="${est == 'COMPLETADO'}"><i class="fas fa-check"></i> Completado</c:when>
                            <c:when test="${est == 'QUIERO_JUGAR'}"><i class="fas fa-star"></i> Quiero Jugar</c:when>
                            <c:when test="${est == 'ABANDONADO'}"><i class="fas fa-times"></i> Abandonado</c:when>
                        </c:choose>
                    </a>
                </c:forEach>
            </div>

            <!--lista de juegos-->
            <div class="library-list">
                <c:forEach var="uj" items="${juegos}">
                    <div class="library-item glass-card" id="lib-${uj.idUsuarioJuego}">
                        <div class="library-item-img">
                            <c:choose>
                                <c:when test="${not empty uj.juego.imgUrl}">
                                    <img src="${uj.juego.imgUrl}" alt="${uj.juego.nombre}" loading="lazy">
                                </c:when>
                                <c:otherwise>
                                    <div class="no-img"><i class="fas fa-gamepad"></i></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="library-item-content">
                            <div class="library-item-header">
                                <h3>
                                    <a href="${pageContext.request.contextPath}/juego?apiId=${uj.juego.apiId}">
                                        ${uj.juego.nombre}
                                    </a>
                                </h3>
                                <span class="status-badge status-${uj.estado}">
                                    <c:choose>
                                        <c:when test="${uj.estado == 'JUGANDO'}"><i class="fas fa-play"></i> Jugando</c:when>
                                        <c:when test="${uj.estado == 'COMPLETADO'}"><i class="fas fa-check"></i> Completado</c:when>
                                        <c:when test="${uj.estado == 'QUIERO_JUGAR'}"><i class="fas fa-star"></i> Quiero Jugar</c:when>
                                        <c:when test="${uj.estado == 'ABANDONADO'}"><i class="fas fa-times"></i> Abandonado</c:when>
                                    </c:choose>
                                </span>
                            </div>

                            <div class="library-item-meta">
                                <c:if test="${uj.plataforma != null}">
                                    <span class="platform-tag small">
                                        <i class="fas fa-desktop"></i> ${uj.plataforma.nombre}
                                    </span>
                                </c:if>
                                <c:if test="${uj.calificacion != null}">
                                    <span class="rating">
                                        <i class="fas fa-star"></i> ${uj.calificacion}/10
                                    </span>
                                </c:if>
                                <span class="votes">
                                    <i class="fas fa-thumbs-up"></i> ${uj.votosPositivos}
                                    <i class="fas fa-thumbs-down"></i> ${uj.votosNegativos}
                                </span>
                            </div>

                            <c:if test="${not empty uj.resena}">
                                <p class="review-text">${uj.resena}</p>
                            </c:if>

                            <!--formulario de actualizacion-->
                            <div class="library-item-actions">
                                <button class="btn btn-small btn-secondary" onclick="toggleEdit(${uj.idUsuarioJuego})" id="btnEdit-${uj.idUsuarioJuego}">
                                    <i class="fas fa-edit"></i> Editar
                                </button>
                                <form action="${pageContext.request.contextPath}/eliminar-juego" method="post" class="inline-form">
                                    <input type="hidden" name="idUsuarioJuego" value="${uj.idUsuarioJuego}">
                                    <button type="submit" class="btn btn-small btn-danger" id="btnEliminar-${uj.idUsuarioJuego}"
                                            onclick="return confirm('¿Eliminar este juego de tu biblioteca?')">
                                        <i class="fas fa-trash"></i> Eliminar
                                    </button>
                                </form>
                            </div>

                            <!--panel de edicion esta oculto por defecto-->
                            <div class="edit-panel" id="editPanel-${uj.idUsuarioJuego}" style="display:none;">
                                <form action="${pageContext.request.contextPath}/actualizar-juego" method="post" class="edit-form">
                                    <input type="hidden" name="idUsuarioJuego" value="${uj.idUsuarioJuego}">
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label>Estado</label>
                                            <select name="estado">
                                                <option value="JUGANDO" ${uj.estado == 'JUGANDO' ? 'selected' : ''}>Jugando</option>
                                                <option value="COMPLETADO" ${uj.estado == 'COMPLETADO' ? 'selected' : ''}>Completado</option>
                                                <option value="QUIERO_JUGAR" ${uj.estado == 'QUIERO_JUGAR' ? 'selected' : ''}>Quiero Jugar</option>
                                                <option value="ABANDONADO" ${uj.estado == 'ABANDONADO' ? 'selected' : ''}>Abandonado</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Calificación (1-10)</label>
                                            <input type="number" name="calificacion" min="1" max="10"
                                                   value="${uj.calificacion != null ? uj.calificacion : ''}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>Reseña</label>
                                        <textarea name="resena" rows="3" placeholder="Escribe tu reseña...">${uj.resena}</textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary btn-small">
                                        <i class="fas fa-save"></i> Guardar
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty juegos}">
                    <div class="empty-state">
                        <i class="fas fa-book-open fa-3x"></i>
                        <h2>Tu biblioteca está vacía</h2>
                        <p>Busca juegos y agrégalos a tu biblioteca</p>
                        <a href="${pageContext.request.contextPath}/buscar" class="btn btn-primary">
                            <i class="fas fa-search"></i> Buscar Juegos
                        </a>
                    </div>
                </c:if>
            </div>
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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Comunidad PlayLog — Rankings globales de juegos">
    <title>PlayLog — Comunidad</title>
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
                <a href="${pageContext.request.contextPath}/comunidad" class="nav-link active" id="navComunidad">
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
                <i class="fas fa-trophy"></i> Comunidad — Rankings Globales
            </h1>

            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <i class="fas fa-exclamation-circle"></i>
                    <span>${error}</span>
                </div>
            </c:if>

            <!--tabs-->
            <div class="community-tabs">
                <button class="tab-btn active" onclick="showTab('deseados')" id="tabDeseados">
                    <i class="fas fa-fire"></i> Más Deseados
                </button>
                <button class="tab-btn" onclick="showTab('votados')" id="tabVotados">
                    <i class="fas fa-thumbs-up"></i> Mejor Votados
                </button>
                <button class="tab-btn" onclick="showTab('completados')" id="tabCompletados">
                    <i class="fas fa-check-circle"></i> Más Completados
                </button>
                <button class="tab-btn" onclick="showTab('resenas')" id="tabResenas">
                    <i class="fas fa-comments"></i> Últimas Reseñas
                </button>
            </div>

            <!--mas desedos-->
            <div class="tab-content active" id="tab-deseados">
                <div class="ranking-list">
                    <c:forEach var="juego" items="${masDeseados}" varStatus="st">
                        <a href="${pageContext.request.contextPath}/juego?apiId=${juego.apiId}"
                           class="ranking-item glass-card" id="rankDeseado-${st.index}">
                            <span class="ranking-position">#${st.index + 1}</span>
                            <div class="ranking-img">
                                <c:choose>
                                    <c:when test="${not empty juego.imgUrl}">
                                        <img src="${juego.imgUrl}" alt="${juego.nombre}" loading="lazy">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-img small"><i class="fas fa-gamepad"></i></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="ranking-info">
                                <h3>${juego.nombre}</h3>
                                <c:if test="${juego.metacritic != null}">
                                    <span class="metacritic-badge small">${juego.metacritic}</span>
                                </c:if>
                            </div>
                        </a>
                    </c:forEach>
                    <c:if test="${empty masDeseados}">
                        <div class="empty-state small">
                            <p>No hay datos disponibles aún.</p>
                        </div>
                    </c:if>
                </div>
            </div>

            <!--mejor votados-->
            <div class="tab-content" id="tab-votados">
                <div class="ranking-list">
                    <c:forEach var="juego" items="${mejorVotados}" varStatus="st">
                        <a href="${pageContext.request.contextPath}/juego?apiId=${juego.apiId}"
                           class="ranking-item glass-card" id="rankVotado-${st.index}">
                            <span class="ranking-position">#${st.index + 1}</span>
                            <div class="ranking-img">
                                <c:choose>
                                    <c:when test="${not empty juego.imgUrl}">
                                        <img src="${juego.imgUrl}" alt="${juego.nombre}" loading="lazy">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-img small"><i class="fas fa-gamepad"></i></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="ranking-info">
                                <h3>${juego.nombre}</h3>
                                <c:if test="${juego.metacritic != null}">
                                    <span class="metacritic-badge small">${juego.metacritic}</span>
                                </c:if>
                            </div>
                        </a>
                    </c:forEach>
                    <c:if test="${empty mejorVotados}">
                        <div class="empty-state small">
                            <p>No hay datos disponibles aún.</p>
                        </div>
                    </c:if>
                </div>
            </div>

            <!--mas compeltaods-->
            <div class="tab-content" id="tab-completados">
                <div class="ranking-list">
                    <c:forEach var="juego" items="${masCompletados}" varStatus="st">
                        <a href="${pageContext.request.contextPath}/juego?apiId=${juego.apiId}"
                           class="ranking-item glass-card" id="rankCompletado-${st.index}">
                            <span class="ranking-position">#${st.index + 1}</span>
                            <div class="ranking-img">
                                <c:choose>
                                    <c:when test="${not empty juego.imgUrl}">
                                        <img src="${juego.imgUrl}" alt="${juego.nombre}" loading="lazy">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-img small"><i class="fas fa-gamepad"></i></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="ranking-info">
                                <h3>${juego.nombre}</h3>
                                <c:if test="${juego.metacritic != null}">
                                    <span class="metacritic-badge small">${juego.metacritic}</span>
                                </c:if>
                            </div>
                        </a>
                    </c:forEach>
                    <c:if test="${empty masCompletados}">
                        <div class="empty-state small">
                            <p>No hay datos disponibles aún.</p>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <!--ultimas resenias-->
        <div class="tab-content" id="tab-resenas">
            <div class="reviews-feed">
                <c:forEach var="uj" items="${ultimasResenas}">
                    <div class="review-card glass-card" id="comReview-${uj.idUsuarioJuego}">
                        <div class="review-card-left">
                            <c:if test="${not empty uj.juego.imgUrl}">
                                <a href="${pageContext.request.contextPath}/juego?apiId=${uj.juego.apiId}" class="review-game-thumb-link">
                                    <img src="${uj.juego.imgUrl}" alt="${uj.juego.nombre}" class="review-game-thumb">
                                </a>
                            </c:if>
                        </div>
                        <div class="review-card-body">
                            <div class="review-card-header">
                                <a href="${pageContext.request.contextPath}/perfil?id=${uj.fkUsuario}" class="review-user" title="Ver perfil de ${uj.usuario.nombre}">
                                    <i class="fas fa-user-circle"></i>
                                    <span>${uj.usuario.nombre}</span>
                                </a>
                                <span class="status-badge status-${uj.estado}">
                                        <c:choose>
                                            <c:when test="${uj.estado == 'JUGANDO'}">Jugando</c:when>
                                            <c:when test="${uj.estado == 'COMPLETADO'}">Completado</c:when>
                                            <c:when test="${uj.estado == 'QUIERO_JUGAR'}">Quiero Jugar</c:when>
                                            <c:when test="${uj.estado == 'ABANDONADO'}">Abandonado</c:when>
                                        </c:choose>
                                    </span>
                            </div>
                            <a href="${pageContext.request.contextPath}/juego?apiId=${uj.juego.apiId}" class="review-game-name">
                                    ${uj.juego.nombre}
                            </a>
                            <c:if test="${uj.calificacion != null}">
                                <span class="rating"><i class="fas fa-star"></i> ${uj.calificacion}/10</span>
                            </c:if>
                            <p class="review-text">${uj.resena}</p>
                            <div class="review-card-footer">
                                <div class="review-votes">
                                    <form action="${pageContext.request.contextPath}/voto" method="post" class="inline-form">
                                        <input type="hidden" name="idUsuarioJuego" value="${uj.idUsuarioJuego}">
                                        <input type="hidden" name="positivo" value="true">
                                        <input type="hidden" name="redirigir" value="/comunidad">
                                        <button type="submit" class="vote-btn vote-up" title="Me gusta">
                                            <i class="fas fa-thumbs-up"></i> ${uj.votosPositivos}
                                        </button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/voto" method="post" class="inline-form">
                                        <input type="hidden" name="idUsuarioJuego" value="${uj.idUsuarioJuego}">
                                        <input type="hidden" name="positivo" value="false">
                                        <input type="hidden" name="redirigir" value="/comunidad">
                                        <button type="submit" class="vote-btn vote-down" title="No me gusta">
                                            <i class="fas fa-thumbs-down"></i> ${uj.votosNegativos}
                                        </button>
                                    </form>
                                </div>
                                <a href="${pageContext.request.contextPath}/perfil?id=${uj.fkUsuario}" class="review-profile-link">
                                    Ver perfil <i class="fas fa-arrow-right"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty ultimasResenas}">
                    <div class="empty-state small">
                        <p>Aún no hay reseñas. ¡Sé el primero en escribir una!</p>
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

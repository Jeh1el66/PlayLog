<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="PlayLog — Lleva el control de todos tus juegos">
    <title>PlayLog — Tu Biblioteca Gaming Personal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/estilo.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
</head>
<body class="landing-page">
<!-- Navbar Pública -->
<nav class="navbar glass-nav">
    <div class="nav-container">
        <a href="${pageContext.request.contextPath}/" class="nav-logo" id="navLogo">
            <i class="fas fa-gamepad"></i> PlayLog
        </a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/login" class="nav-link">Iniciar Sesión</a>
            <a href="${pageContext.request.contextPath}/registro" class="btn btn-primary" style="margin-left: 1rem;">Comenzar Gratis</a>
        </div>
    </div>
</nav>
<main class="main-content">
    <!-- Sección Hero -->
    <section class="hero-section text-center">
        <div class="container">
            <div class="hero-content glass-card" style="padding: 4rem 2rem; max-width: 800px; margin: 0 auto; margin-top: 4rem;">
                <div class="logo-icon hero-icon" style="font-size: 4rem; color: var(--accent-primary); margin-bottom: 1.5rem;">
                    <i class="fas fa-gamepad"></i>
                </div>
                <h1 class="hero-title">Tu Vida Gaming,<br><span class="text-gradient">En Un Solo Lugar</span></h1>
                <p class="hero-subtitle" style="font-size: 1.25rem; color: var(--text-secondary); margin: 1.5rem auto 3rem auto; line-height: 1.6; text-align: center; max-width: 600px;">
                    PlayLog es la plataforma definitiva para llevar un registro de los juegos que estás jugando,
                    los que has completado y los que quieres jugar. Únete a la comunidad.
                </p>
                <div class="hero-actions" style="display: flex; gap: 1.5rem; justify-content: center; flex-wrap: wrap;">
                    <a href="${pageContext.request.contextPath}/registro" class="btn btn-primary" style="font-size: 1.25rem; padding: 1rem 2rem;">
                        <i class="fas fa-rocket"></i> Crear mi cuenta
                    </a>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary" style="font-size: 1.25rem; padding: 1rem 2rem;">
                        <i class="fas fa-sign-in-alt"></i> Ya tengo cuenta
                    </a>
                </div>
            </div>
        </div>
    </section>
    <!-- Sección de Características -->
    <section class="features-section" style="margin-top: 5rem; margin-bottom: 5rem;">
        <div class="container">
            <h2 class="text-center" style="margin-bottom: 3rem; font-size: 2.5rem; font-weight: 700;">Descubre lo que puedes hacer</h2>
            <div class="dashboard-grid">
                <div class="stat-card glass-card text-center" style="padding: 2.5rem 1.5rem;">
                    <div class="stat-icon" style="background: rgba(147, 51, 234, 0.1); color: var(--accent-primary); width: 80px; height: 80px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 2.5rem; margin: 0 auto 1.5rem auto;">
                        <i class="fas fa-database"></i>
                    </div>
                    <h3 style="font-size: 1.5rem; margin-bottom: 1rem;">Base de datos masiva</h3>
                    <p style="color: var(--text-secondary);">Conectado con RAWG para ofrecerte información de más de 350,000 juegos al instante.</p>
                </div>

                <div class="stat-card glass-card text-center" style="padding: 2.5rem 1.5rem;">
                    <div class="stat-icon" style="background: rgba(59, 130, 246, 0.1); color: #3b82f6; width: 80px; height: 80px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 2.5rem; margin: 0 auto 1.5rem auto;">
                        <i class="fas fa-list-check"></i>
                    </div>
                    <h3 style="font-size: 1.5rem; margin-bottom: 1rem;">Organiza tu biblioteca</h3>
                    <p style="color: var(--text-secondary);">Marca juegos como Jugando, Completados o Quiero Jugar. No pierdas el rastro de tu backlog.</p>
                </div>

                <div class="stat-card glass-card text-center" style="padding: 2.5rem 1.5rem;">
                    <div class="stat-icon" style="background: rgba(16, 185, 129, 0.1); color: #10b981; width: 80px; height: 80px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 2.5rem; margin: 0 auto 1.5rem auto;">
                        <i class="fas fa-star"></i>
                    </div>
                    <h3 style="font-size: 1.5rem; margin-bottom: 1rem;">Reseñas y Comunidad</h3>
                    <p style="color: var(--text-secondary);">Escribe tus opiniones, dale calificación a los juegos y descubre qué está jugando la comunidad.</p>
                </div>
            </div>
        </div>
    </section>
</main>
<footer class="footer glass-nav">
    <div class="container text-center">
        <p>&copy; 2026 PlayLog — Tu biblioteca gaming personal</p>
    </div>
</footer>
<script src="${pageContext.request.contextPath}/recursos/js/app.js"></script>
</body>
</html>
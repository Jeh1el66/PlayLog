<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Inicia sesión en PlayLog — tu plataforma de seguimiento de videojuegos">
    <title>PlayLog — Iniciar Sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/estilo.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
</head>
<body class="auth-page">
    <div class="auth-container">
        <div class="auth-card glass-card">
            <div class="auth-header">
                <div class="logo-icon">
                    <i class="fas fa-gamepad"></i>
                </div>
                <h1>PlayLog</h1>
                <p class="auth-subtitle">Tu biblioteca de juegos personal</p>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <i class="fas fa-exclamation-circle"></i>
                    <span>${error}</span>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form" id="loginForm">
                <div class="form-group">
                    <label for="email">
                        <i class="fas fa-envelope"></i> Email
                    </label>
                    <input type="email" id="email" name="email" required
                           placeholder="tu@email.com" autocomplete="email">
                </div>

                <div class="form-group">
                    <label for="password">
                        <i class="fas fa-lock"></i> Contraseña
                    </label>
                    <input type="password" id="password" name="password" required
                           placeholder="••••••••" autocomplete="current-password">
                </div>

                <button type="submit" class="btn btn-primary btn-block" id="btnLogin">
                    <i class="fas fa-sign-in-alt"></i> Iniciar Sesión
                </button>
            </form>

            <div class="auth-footer">
                <p>¿No tienes cuenta? <a href="${pageContext.request.contextPath}/registro" id="linkRegistro">Regístrate aquí</a></p>
            </div>
        </div>
    </div>
</body>
</html>

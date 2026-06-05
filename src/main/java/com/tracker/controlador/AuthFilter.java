package com.tracker.controlador;

import com.tracker.servicio.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


 //filtro de autenticacion que intercepta todas las rutas excepto /login y /registro
 //valida el JWT desde una cookie httpOnly

@WebFilter("/*")
public class AuthFilter implements Filter {

    private AuthService authService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authService = new AuthService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getServletPath();

        //rutas publicas que no requieren autenticacion
        if (path.equals("/") || path.equals("/index.jsp") ||
                path.equals("/login") || path.equals("/registro") ||
            path.startsWith("/recursos/") || path.equals("/favicon.ico")) {
            chain.doFilter(request, response);
            return;
        }

        //buscar cookie jwt
        String token = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("jwt".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Claims claims = authService.validarToken(token);
        if (claims == null) {
            //token invalido, borrar cookie y redirigir
            Cookie cookie = new Cookie("jwt", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        //token valido: guardar datos del usuario en el request
        req.setAttribute("idUsuario", Integer.parseInt(claims.getSubject()));
        req.setAttribute("nombreUsuario", claims.get("nombre", String.class));

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

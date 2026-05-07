package com.tracker.controlador;

import com.tracker.dao.UsuarioDAO;
import com.tracker.modelo.Usuario;
import com.tracker.servicio.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


//serervlet para el inicio de sesion
//GET: muestra login.jsp
//POST: valida credenciales y guarda JWT en cookie httpOnly

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;
    private AuthService authService;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
        authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("error", "Email y contraseña son requeridos.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        try {
            Usuario usuario = usuarioDAO.buscarPorEmail(email.trim());

            if (usuario == null || !authService.verificarPassword(password, usuario.getPasswordHash())) {
                req.setAttribute("error", "Email o contraseña incorrectos.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }

            //generar JWT y guardar en cookie httpOnly
            String token = authService.generarToken(usuario.getIdUsuario(), usuario.getNombre());
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(24 * 60 * 60); //24 horas
            cookie.setPath("/");
            resp.addCookie(cookie);

            resp.sendRedirect(req.getContextPath() + "/inicio");

        } catch (Exception e) {
            req.setAttribute("error", "Error interno del servidor.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}

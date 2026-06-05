package com.tracker.controlador;

import com.tracker.dao.UsuarioDAO;
import com.tracker.modelo.Usuario;
import com.tracker.servicio.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


//servlet para el registro de nuevos usuarios.
//GET: muestra registro.jsp
//POST: valida email unico, hashea password e inserta usuario

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

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
        req.getRequestDispatcher("/registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (nombre == null || nombre.isBlank() ||
            email == null || email.isBlank() ||
            password == null || password.isBlank()) {
            req.setAttribute("error", "Todos los campos son requeridos.");
            req.getRequestDispatcher("/registro.jsp").forward(req, resp);
            return;
        }

        try {
            //verificar que el email no exista
            if (usuarioDAO.buscarPorEmail(email.trim()) != null) {
                req.setAttribute("error", "Ya existe una cuenta con ese email.");
                req.getRequestDispatcher("/registro.jsp").forward(req, resp);
                return;
            }

            //crear usuario con contra hasheada
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre.trim());
            usuario.setEmail(email.trim());
            usuario.setPasswordHash(authService.hashPassword(password));

            usuarioDAO.insertar(usuario);

            //generar JWT y guardar en cookie httpOnly
            String token = authService.generarToken(usuario.getIdUsuario(), usuario.getNombre());
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            resp.addCookie(cookie);

            resp.sendRedirect(req.getContextPath() + "/inicio");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error al registrar usuario.");
            req.getRequestDispatcher("/registro.jsp").forward(req, resp);
        }
    }
}

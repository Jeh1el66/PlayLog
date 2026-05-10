package com.tracker.controlador;

import com.tracker.dao.UsuarioJuegoDAO;
import com.tracker.modelo.EstadoJuego;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


 //Servlet para actualizar un juego en la biblioteca del usuario.
 //POST: recibe idUsuarioJuego, estado, calificacion y resena.

@WebServlet("/actualizar-juego")
public class ActualizarJuegoServlet extends HttpServlet {

    private UsuarioJuegoDAO usuarioJuegoDAO;

    @Override
    public void init() throws ServletException {
        usuarioJuegoDAO = new UsuarioJuegoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idUJStr = req.getParameter("idUsuarioJuego");
        String estadoStr = req.getParameter("estado");
        String calStr = req.getParameter("calificacion");
        String resena = req.getParameter("resena");

        if (idUJStr == null || estadoStr == null) {
            resp.sendRedirect(req.getContextPath() + "/biblioteca");
            return;
        }

        try {
            int idUsuarioJuego = Integer.parseInt(idUJStr);
            EstadoJuego estado = EstadoJuego.valueOf(estadoStr.toUpperCase());
            Integer calificacion = null;
            if (calStr != null && !calStr.isBlank()) {
                calificacion = Integer.parseInt(calStr);
                if (calificacion < 1 || calificacion > 10) {
                    calificacion = null;
                }
            }

            usuarioJuegoDAO.actualizar(idUsuarioJuego, estado, calificacion, resena);

            resp.sendRedirect(req.getContextPath() + "/biblioteca");

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/biblioteca?error=No+se+pudo+actualizar");
        }
    }
}

package com.tracker.controlador;

import com.tracker.dao.UsuarioJuegoDAO;
import com.tracker.modelo.EstadoJuego;
import com.tracker.modelo.UsuarioJuego;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


 //servlet para agregar un juego a la biblioteca del user
 //POST: recibe idJuego, idPlataforma y estado

@WebServlet("/agregar-juego")
public class AgregarJuegoServlet extends HttpServlet {

    private UsuarioJuegoDAO usuarioJuegoDAO;

    @Override
    public void init() throws ServletException {
        usuarioJuegoDAO = new UsuarioJuegoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idUsuario = (int) req.getAttribute("idUsuario");
        String idJuegoStr = req.getParameter("idJuego");
        String idPlataformaStr = req.getParameter("idPlataforma");
        String estadoStr = req.getParameter("estado");

        if (idJuegoStr == null || estadoStr == null) {
            resp.sendRedirect(req.getContextPath() + "/biblioteca");
            return;
        }

        try {
            UsuarioJuego uj = new UsuarioJuego();
            uj.setFkUsuario(idUsuario);
            uj.setFkJuego(Integer.parseInt(idJuegoStr));
            uj.setEstado(EstadoJuego.valueOf(estadoStr.toUpperCase()));

            if (idPlataformaStr != null && !idPlataformaStr.isBlank()) {
                uj.setFkPlataforma(Integer.parseInt(idPlataformaStr));
            }

            usuarioJuegoDAO.agregar(uj);

            resp.sendRedirect(req.getContextPath() + "/biblioteca");

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/biblioteca?error=No+se+pudo+agregar+el+juego");
        }
    }
}

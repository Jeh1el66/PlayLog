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

            //calificacionn del 1 al 10
            String calStr = req.getParameter("calificacion");
            if (calStr != null && !calStr.isBlank()) {
                int cal = Integer.parseInt(calStr);
                if (cal >= 1 && cal <= 10) {
                    uj.setCalificacion(cal);
                }
            }

            //resenia
            String resena = req.getParameter("resena");
            if (resena != null && !resena.isBlank()) {
                uj.setResena(resena.trim());
            }

            usuarioJuegoDAO.agregar(uj);

            resp.sendRedirect(req.getContextPath() + "/biblioteca");

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/biblioteca?error=No+se+pudo+agregar+el+juego");
        }
    }
}

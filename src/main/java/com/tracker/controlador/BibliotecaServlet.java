package com.tracker.controlador;

import com.tracker.dao.UsuarioJuegoDAO;
import com.tracker.dao.VotoDAO;
import com.tracker.modelo.EstadoJuego;
import com.tracker.modelo.UsuarioJuego;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;


//Servlet para la biblioteca del usuario
//GET: lista todos los juegos del usuario autenticado, soporta filtro por estado

@WebServlet("/biblioteca")
public class BibliotecaServlet extends HttpServlet {

    private UsuarioJuegoDAO usuarioJuegoDAO;
    private VotoDAO votoDAO;

    @Override
    public void init() throws ServletException {
        usuarioJuegoDAO = new UsuarioJuegoDAO();
        votoDAO = new VotoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idUsuario = (int) req.getAttribute("idUsuario");
        String estadoParam = req.getParameter("estado");

        try {
            List<UsuarioJuego> juegos;

            if (estadoParam != null && !estadoParam.isBlank()) {
                try {
                    EstadoJuego estado = EstadoJuego.valueOf(estadoParam.toUpperCase());
                    juegos = usuarioJuegoDAO.filtrarPorEstado(idUsuario, estado);
                    req.setAttribute("estadoFiltro", estadoParam.toUpperCase());
                } catch (IllegalArgumentException e) {
                    juegos = usuarioJuegoDAO.listarPorUsuario(idUsuario);
                }
            } else {
                juegos = usuarioJuegoDAO.listarPorUsuario(idUsuario);
            }

            //cargar votos para cada juego
            for (UsuarioJuego uj : juegos) {
                int[] votos = usuarioJuegoDAO.contarVotos(uj.getIdUsuarioJuego());
                uj.setVotosPositivos(votos[0]);
                uj.setVotosNegativos(votos[1]);
            }

            req.setAttribute("juegos", juegos);
            req.setAttribute("estados", EstadoJuego.values());

        } catch (Exception e) {
            req.setAttribute("error", "Error al cargar la biblioteca.");
        }

        req.getRequestDispatcher("/biblioteca.jsp").forward(req, resp);
    }
}

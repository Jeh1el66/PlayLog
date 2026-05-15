package com.tracker.controlador;

import com.tracker.dao.*;
import com.tracker.modelo.*;
import com.tracker.servicio.EstadisticasService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


//servlet para el perfil del usuario
//GET: recibe idUsuario, obtiene datos, biblioteca publica y estadisticas.

@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;
    private UsuarioJuegoDAO usuarioJuegoDAO;
    private SeguimientoDAO seguimientoDAO;
    private EstadisticasService estadisticasService;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
        usuarioJuegoDAO = new UsuarioJuegoDAO();
        seguimientoDAO = new SeguimientoDAO();
        estadisticasService = new EstadisticasService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int idUsuarioLogueado = (int) req.getAttribute("idUsuario");

        int idPerfil;
        if (idStr != null && !idStr.isBlank()) {
            idPerfil = Integer.parseInt(idStr);
        } else {
            //si no se pasa ID, mostrar el perfil del usuario logueado
            idPerfil = idUsuarioLogueado;
        }

        try {
            Usuario usuario = usuarioDAO.buscarPorId(idPerfil);
            if (usuario == null) {
                req.setAttribute("error", "Usuario no encontrado.");
                req.getRequestDispatcher("/perfil.jsp").forward(req, resp);
                return;
            }

            List<UsuarioJuego> biblioteca = usuarioJuegoDAO.listarPorUsuario(idPerfil);
            Map<String, Integer> stats = estadisticasService.statsDeUsuario(idPerfil);
            List<Usuario> seguidores = seguimientoDAO.obtenerSeguidores(idPerfil);
            List<Usuario> seguidos = seguimientoDAO.obtenerSeguidos(idPerfil);

            //verificar si el usuario logueado sigue a este perfil
            boolean yaSigue = false;
            if (idUsuarioLogueado != idPerfil) {
                yaSigue = seguimientoDAO.existeSeguimiento(idUsuarioLogueado, idPerfil);
            }

            req.setAttribute("perfilUsuario", usuario);
            req.setAttribute("biblioteca", biblioteca);
            req.setAttribute("stats", stats);
            req.setAttribute("seguidores", seguidores);
            req.setAttribute("seguidos", seguidos);
            req.setAttribute("yaSigue", yaSigue);
            req.setAttribute("esMiPerfil", idUsuarioLogueado == idPerfil);

        } catch (Exception e) {
            req.setAttribute("error", "Error al cargar el perfil.");
        }

        req.getRequestDispatcher("/perfil.jsp").forward(req, resp);
    }
}

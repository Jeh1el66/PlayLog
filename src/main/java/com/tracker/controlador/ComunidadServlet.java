package com.tracker.controlador;

import com.tracker.modelo.Juego;
import com.tracker.servicio.EstadisticasService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


//servlet para la página de comunida
//GET: muestra estadisticas globales usando EstadisticasService
@WebServlet("/comunidad")
public class ComunidadServlet extends HttpServlet {

    private EstadisticasService estadisticasService;

    @Override
    public void init() throws ServletException {
        estadisticasService = new EstadisticasService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Juego> masDeseados = estadisticasService.juegosMasDeseados(20);
            List<Juego> mejorVotados = estadisticasService.mejoresVotados(20);
            List<Juego> masCompletados = estadisticasService.masCompletados(20);

            req.setAttribute("masDeseados", masDeseados);
            req.setAttribute("mejorVotados", mejorVotados);
            req.setAttribute("masCompletados", masCompletados);
        } catch (Exception e) {
            req.setAttribute("masDeseados", Collections.emptyList());
            req.setAttribute("mejorVotados", Collections.emptyList());
            req.setAttribute("masCompletados", Collections.emptyList());
            req.setAttribute("error", "No se pudieron cargar las estadísticas de la comunidad.");
        }

        req.getRequestDispatcher("/comunidad.jsp").forward(req, resp);
    }
}

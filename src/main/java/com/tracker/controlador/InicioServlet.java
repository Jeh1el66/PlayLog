package com.tracker.controlador;

import com.tracker.modelo.Juego;
import com.tracker.servicio.EstadisticasService;
import jakarta.servlet.ServletException;
import com.tracker.modelo.UsuarioJuego;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


 //Servlet para la pagina principala
 //GET: muestra inicio.jsp con juegos mas deseados, mejor votados y mas completados

@WebServlet("/inicio")
public class InicioServlet extends HttpServlet {

    private EstadisticasService estadisticasService;

    @Override
    public void init() throws ServletException {
        estadisticasService = new EstadisticasService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Juego> masDeseados = estadisticasService.juegosMasDeseados(10);
            List<Juego> mejorVotados = estadisticasService.mejoresVotados(10);
            List<Juego> masCompletados = estadisticasService.masCompletados(10);
            List<UsuarioJuego> ultimasResenas = estadisticasService.ultimasResenas(5);

            req.setAttribute("masDeseados", masDeseados);
            req.setAttribute("mejorVotados", mejorVotados);
            req.setAttribute("masCompletados", masCompletados);
            req.setAttribute("ultimasResenas", ultimasResenas);
        } catch (Exception e) {
            req.setAttribute("masDeseados", Collections.emptyList());
            req.setAttribute("mejorVotados", Collections.emptyList());
            req.setAttribute("masCompletados", Collections.emptyList());
            req.setAttribute("ultimasResenas", Collections.emptyList());
            req.setAttribute("error", "No se pudieron cargar las estadísticas.");
        }

        req.getRequestDispatcher("/inicio.jsp").forward(req, resp);
    }
}

package com.tracker.controlador;

import com.tracker.dao.JuegoDAO;
import com.tracker.modelo.Juego;
import com.tracker.servicio.RawgService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;


//Servlet para busqueda de juegos
//GET: recibe parametro q, busca en BD y en RAWG, combina resultados sin duplicados

@WebServlet("/buscar")
public class BuscarServlet extends HttpServlet {
    private JuegoDAO juegoDAO;
    private RawgService rawgService;
    @Override
    public void init() throws ServletException {
        juegoDAO = new JuegoDAO();
        rawgService = new RawgService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String query = req.getParameter("q");
        if (query != null && !query.isBlank()) {
            query = query.trim();
            req.setAttribute("query", query);
            try {
                //buscar en BD local
                List<Juego> resultadosBD = juegoDAO.buscarPorNombre(query);
                //buscar siempre en RAWG tambien
                List<Juego> resultadosRawg = new ArrayList<>();
                try {
                    resultadosRawg = rawgService.buscarJuegos(query);
                } catch (Exception ignored) {
                    //si falla RAWG, continuar solo con BD
                }
                //combinar: primero BD, luego RAWG sin duplicados
                Set<String> apiIdsYaIncluidos = new HashSet<>();
                List<Juego> resultados = new ArrayList<>();
                for (Juego j : resultadosBD) {
                    resultados.add(j);
                    if (j.getApiId() != null) {
                        apiIdsYaIncluidos.add(j.getApiId());
                    }
                }
                for (Juego j : resultadosRawg) {
                    if (j.getApiId() != null && !apiIdsYaIncluidos.contains(j.getApiId())) {
                        resultados.add(j);
                        apiIdsYaIncluidos.add(j.getApiId());
                    }
                }
                if (resultados.isEmpty()) {
                    req.setAttribute("mensaje", "No se encontraron resultados para \"" + query + "\".");
                }
                req.setAttribute("resultados", resultados);
            } catch (Exception e) {
                req.setAttribute("error", "Error al buscar juegos.");
            }
        }
        req.getRequestDispatcher("/buscar.jsp").forward(req, resp);
    }
}

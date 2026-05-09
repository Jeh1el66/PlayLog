package com.tracker.controlador;

import com.tracker.dao.JuegoDAO;
import com.tracker.modelo.Juego;
import com.tracker.servicio.RawgService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;


//Servlet para busqueda de juegos
//GET: recibe parametro q, busca primero en BD, si no hay resultados llama a RAWG

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
                //buscar primero en bd
                List<Juego> resultados = juegoDAO.buscarPorNombre(query);

                if (resultados.isEmpty()) {
                    //si no hay en bd, buscar en RAWG
                    resultados = rawgService.buscarJuegos(query);
                    if (resultados.isEmpty()) {
                        req.setAttribute("mensaje", "No se encontraron resultados para \"" + query + "\".");
                    } else {
                        req.setAttribute("fuenteRawg", true);
                    }
                }

                req.setAttribute("resultados", resultados);

            } catch (Exception e) {
                req.setAttribute("error", "Error al buscar juegos.");
            }
        }

        req.getRequestDispatcher("/buscar.jsp").forward(req, resp);
    }
}

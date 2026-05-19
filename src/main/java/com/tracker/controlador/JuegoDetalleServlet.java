package com.tracker.controlador;

import com.tracker.dao.*;
import com.tracker.modelo.*;
import com.tracker.servicio.RawgService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;


//servlet para el detalle de un juego
//GET: recibe apiId, guarda el juego si no existe, muestra detalle con géneros y plataformas

@WebServlet("/juego")
public class JuegoDetalleServlet extends HttpServlet {

    private JuegoDAO juegoDAO;
    private GeneroDAO generoDAO;
    private PlataformaDAO plataformaDAO;
    private RawgService rawgService;

    @Override
    public void init() throws ServletException {
        juegoDAO = new JuegoDAO();
        generoDAO = new GeneroDAO();
        plataformaDAO = new PlataformaDAO();
        rawgService = new RawgService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String apiId = req.getParameter("apiId");

        if (apiId == null || apiId.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/inicio");
            return;
        }

        try {
            //buscar si ya existe en BD
            Juego juego = juegoDAO.buscarPorApiId(apiId.trim());

            if (juego == null) {
                //obtener detalle de RAWG
                Juego rawgJuego = rawgService.obtenerDetalle(apiId.trim());
                if (rawgJuego == null) {
                    req.setAttribute("error", "No se pudo obtener información del juego.");
                    req.getRequestDispatcher("/juego_detalle.jsp").forward(req, resp);
                    return;
                }

                //guardar juego en BD
                juego = juegoDAO.guardarSiNoExiste(rawgJuego);

                //guardar generos y asociarlos
                for (Genero g : rawgJuego.getGeneros()) {
                    Genero genero = generoDAO.insertar(g);
                    generoDAO.agregarAJuego(juego.getIdJuego(), genero.getIdGenero());
                }

                //guardar plataformas y asociarlas
                for (Plataforma p : rawgJuego.getPlataformas()) {
                    Plataforma plat = plataformaDAO.insertar(p);
                    plataformaDAO.agregarAJuego(juego.getIdJuego(), plat.getIdPlataforma());
                }
            }

            //cargar géneros y plataformas del juego
            List<Genero> generos = generoDAO.listarPorJuego(juego.getIdJuego());
            List<Plataforma> plataformas = plataformaDAO.listarPorJuego(juego.getIdJuego());
            juego.setGeneros(generos);
            juego.setPlataformas(plataformas);

            //cargar todas las plataformas para el formulario de agregar
            List<Plataforma> todasPlataformas = plataformaDAO.listarTodas();

            req.setAttribute("juego", juego);
            req.setAttribute("todasPlataformas", todasPlataformas);

        } catch (Exception e) {
            req.setAttribute("error", "Error al cargar el detalle del juego.");
        }

        req.getRequestDispatcher("/juego_detalle.jsp").forward(req, resp);
    }
}

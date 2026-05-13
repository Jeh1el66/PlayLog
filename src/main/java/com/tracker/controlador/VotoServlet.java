package com.tracker.controlador;

import com.tracker.dao.UsuarioJuegoDAO;
import com.tracker.dao.VotoDAO;
import com.tracker.modelo.UsuarioJuego;
import com.tracker.modelo.Voto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


//servlet para votar en un registro de usuario_juego
//POST: recibe idUsuarioJuego y positivo (true/false)
//si ya existe voto, lo cambia o lo quita
//valida que un usuario no vote su propio track
@WebServlet("/voto")
public class VotoServlet extends HttpServlet {

    private VotoDAO votoDAO;
    private UsuarioJuegoDAO usuarioJuegoDAO;

    @Override
    public void init() throws ServletException {
        votoDAO = new VotoDAO();
        usuarioJuegoDAO = new UsuarioJuegoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idUsuario = (int) req.getAttribute("idUsuario");
        String idUJStr = req.getParameter("idUsuarioJuego");
        String positivoStr = req.getParameter("positivo");
        String redirigirA = req.getParameter("redirigir");

        if (idUJStr == null || positivoStr == null) {
            resp.sendRedirect(req.getContextPath() + "/biblioteca");
            return;
        }

        try {
            int idUsuarioJuego = Integer.parseInt(idUJStr);
            boolean positivo = Boolean.parseBoolean(positivoStr);

            //validar que no vote su propio track
            UsuarioJuego uj = usuarioJuegoDAO.buscar(idUsuarioJuego);
            if (uj != null && uj.getFkUsuario() == idUsuario) {
                //no puede votar su propio registro
                resp.sendRedirect(req.getContextPath() + "/biblioteca?error=No+puedes+votar+tu+propio+registro");
                return;
            }

            //verificar si ya existe voto
            Voto votoExistente = votoDAO.buscarVoto(idUsuario, idUsuarioJuego);
            if (votoExistente != null) {
                if (votoExistente.isPositivo() == positivo) {
                    //mismo voto: quitar
                    votoDAO.quitarVoto(idUsuario, idUsuarioJuego);
                } else {
                    //diferente: cambiar
                    votoDAO.cambiarVoto(idUsuario, idUsuarioJuego, positivo);
                }
            } else {
                //nuevo voto
                votoDAO.votar(idUsuario, idUsuarioJuego, positivo);
            }

            if (redirigirA != null && !redirigirA.isBlank()) {
                resp.sendRedirect(req.getContextPath() + redirigirA);
            } else {
                resp.sendRedirect(req.getContextPath() + "/biblioteca");
            }

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/biblioteca");
        }
    }
}

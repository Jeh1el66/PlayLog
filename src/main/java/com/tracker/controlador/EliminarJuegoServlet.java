package com.tracker.controlador;

import com.tracker.dao.UsuarioJuegoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


//Servlet para eliminar un juego de la biblioteca del usuario
//POST: recibe idUsuarioJuego

@WebServlet("/eliminar-juego")
public class EliminarJuegoServlet extends HttpServlet {

    private UsuarioJuegoDAO usuarioJuegoDAO;

    @Override
    public void init() throws ServletException {
        usuarioJuegoDAO = new UsuarioJuegoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idUJStr = req.getParameter("idUsuarioJuego");

        if (idUJStr == null) {
            resp.sendRedirect(req.getContextPath() + "/biblioteca");
            return;
        }

        try {
            int idUsuarioJuego = Integer.parseInt(idUJStr);
            usuarioJuegoDAO.eliminar(idUsuarioJuego);
        } catch (Exception e) {
            System.out.println(e);
        }

        resp.sendRedirect(req.getContextPath() + "/biblioteca");
    }
}

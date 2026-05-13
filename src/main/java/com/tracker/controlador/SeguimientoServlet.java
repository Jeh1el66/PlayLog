package com.tracker.controlador;

import com.tracker.dao.SeguimientoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


//servlet para seguir/dejar de seguir a un usuario.
//POST: recibe accion (seguir o dejarDeSeguir) e idSeguido.
//valida que un usuario no se siga a sí mismo
@WebServlet("/seguimiento")
public class SeguimientoServlet extends HttpServlet {

    private SeguimientoDAO seguimientoDAO;

    @Override
    public void init() throws ServletException {
        seguimientoDAO = new SeguimientoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idUsuario = (int) req.getAttribute("idUsuario");
        String accion = req.getParameter("accion");
        String idSeguidoStr = req.getParameter("idSeguido");

        if (accion == null || idSeguidoStr == null) {
            resp.sendRedirect(req.getContextPath() + "/inicio");
            return;
        }

        try {
            int idSeguido = Integer.parseInt(idSeguidoStr);

            //validar que no se siga a sí mismo
            if (idUsuario == idSeguido) {
                resp.sendRedirect(req.getContextPath() + "/perfil?id=" + idSeguido + "&error=No+puedes+seguirte+a+ti+mismo");
                return;
            }

            if ("seguir".equals(accion)) {
                seguimientoDAO.seguir(idUsuario, idSeguido);
            } else if ("dejarDeSeguir".equals(accion)) {
                seguimientoDAO.dejarDeSeguir(idUsuario, idSeguido);
            }

            resp.sendRedirect(req.getContextPath() + "/perfil?id=" + idSeguido);

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/inicio");
        }
    }
}

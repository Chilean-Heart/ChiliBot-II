package com.team2576.lib.web.handlers;

import com.team2576.lib.web.handlers.Constants;
import com.team2576.lib.util.ConstantsBase.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class ConstantsServlet extends HttpServlet {

    private void buildPage(HttpServletResponse response) throws IOException {
        Constants constants = new Constants();

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.println("<head>");
        out.println("<link rel='stylesheet' type='text/css' href='css/style.css'/>");
        out.println("<script type='text/javascript' src='js/cambiarPestanna.js'></script>");
        out.println("<script type='text/javascript' src='js/jquery-1.7.2.min.js'></script>");
        out.println("</head>");
        out.println("<html>");
        out.println("<body>");
        out.println("<div class='contenedor>'");
        out.println("<div class='titulo'>ChileanHeart FRC2576</div>");
        out.println("<div id='pestanas'>");
        out.println("<ul id='lista'>");
        out.println("<li id='pestana1'><a href='javascript:cambiarPestanna(pestanas,pestana1);'>Constantes</a></li>");
        out.println("<li id='pestana2'><a href='javascript:cambiarPestanna(pestanas,pestana1);'Nvidia TX1</a></li>");
        out.println("</div>");//Fin pestanas
        out.println("<body onload='javascript:cambiarPestanna(pestanas,pestana1);'>");
        out.println("<div id='contenidopestanas'>");
        out.println("<div id='cpestana1'>");
        out.println("<form method='post'>");
        out.println("<table cellspacing='5'>");
        Collection<Constant> cs = constants.getConstants();
        for (Constant c : cs) {
            out.println("<tr>");
            out.println("<td>(" + c.type + ")</td>");
            out.println("<td>" + c.name + "</td>");
            out.println("<td><input type='text' name='" + c.name + "' id='" + c.name + "' value='"
                    + c.value + "'></td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("<input type='submit' value='Save'>");
        out.println("<input type='reset' value='Reset'>");
        out.println("</form>");
        out.println("</div>");
        out.println("<div id='cpestana2'>");
        //Lucas aqui va al wea de TX1
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        buildPage(response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Constants constants = new Constants();
        for (String key : request.getParameterMap().keySet()) {
            String value = request.getParameter(key);
            Constant c = constants.getConstant(key);
            if (c != null) {
                if (double.class.equals(c.type) || Double.class.equals(c.type)) {
                    double v = Double.parseDouble(value);
                    constants.setConstant(key, v);
                } else if (int.class.equals(c.type) || Integer.class.equals(c.type)) {
                    int v = Integer.parseInt(value);
                    constants.setConstant(key, v);
                } else if (String.class.equals(c.type)) {
                    constants.setConstant(key, value);
                }
            }

        }
        constants.saveToFile();
        buildPage(response);
    }
}

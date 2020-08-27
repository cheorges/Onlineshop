package ch.zotteljedi.onlineshop.firststep;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import ch.zotteljedi.onlineshop.firststep.service.MyFirstServiceLocal;
import org.apache.logging.log4j.LogManager;

@WebServlet(name = "MyServlet", urlPatterns = {"/MyServlet"})
public class MyServlet extends HttpServlet {

    @Inject
    private MyFirstStatelessSessionBeanLocal myFirstBean;

    @Inject
    private MyFirstServiceLocal myFirstService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE HTML>");
            out.println("<HTML>");
            out.println("<HEAD>");
            out.println("<TITLE>Hello World</TITLE>");
            out.println("</HEAD>");
            out.println("<BODY>");
            out.println("<H1>HELLO WORLD</H1>");

            out.println("<H2>" + myFirstBean.getMessage() + "</H2>");
            out.println("<p>" + myFirstService.setMessage() + "</p>");
            for (String message : myFirstService.getMessage()) {
                out.println("<p>" + message + "</p>");
            }

            out.println("</BODY>");
            out.println("</HTML>");
            out.flush();
        } catch (Exception e) {
            LogManager.getLogger(MyServlet.class).error("exception", e);
        }

    }
}

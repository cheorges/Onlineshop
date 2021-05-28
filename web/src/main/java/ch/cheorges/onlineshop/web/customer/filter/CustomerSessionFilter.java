package ch.cheorges.onlineshop.web.customer.filter;

import ch.cheorges.onlineshop.web.customer.jsf.CustomerSessionJSF;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CustomerSessionFilter implements Filter {

    @Inject
    private CustomerSessionJSF customerSessionJSF;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getRequestURI().contains("image")
                || httpRequest.getRequestURI().contains("detail")
                || httpRequest.getRequestURI().contains("cart")
                || httpRequest.getRequestURI().contains("overview")
                || httpRequest.getRequestURI().contains("product")
                || httpRequest.getRequestURI().contains("purchase")
                || httpRequest.getRequestURI().contains("purchasedetail")
                || httpRequest.getRequestURI().contains("sales")
                || httpRequest.getRequestURI().contains("salesdetail")
                || httpRequest.getRequestURI().contains("profile")) {
            chain.doFilter(request, response);
            return;
        }

        if (customerSessionJSF.isAuthenticated()
                && (httpRequest.getRequestURI().endsWith("login.xhtml") || httpRequest.getRequestURI().endsWith("register.xhtml"))) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.xhtml");
            dispatcher.forward(request, response);
        } else if (!customerSessionJSF.isAuthenticated()
                && (!httpRequest.getRequestURI().endsWith("login.xhtml")
                && !httpRequest.getRequestURI().endsWith("register.xhtml")
                && !httpRequest.getRequestURI().endsWith("index.xhtml"))) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.xhtml");
            dispatcher.forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

}

package ch.zotteljedi.onlineshop.web.product.servlet;

import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.product.dto.Product;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.common.product.service.ProductServicLocal;
import ch.zotteljedi.onlineshop.web.customer.jsf.CustomerJSF;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ImageServletNew", urlPatterns = {"/image2"})
public class ImageServletNew extends HttpServlet {

   @Inject
   ProductServicLocal productServicLocal;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
         Optional<Product> product;
         String idAsString = request.getParameter("id");
         if (Objects.isNull(idAsString)) {
            product = productServicLocal.getProductById(Id.of(3, ProductId.class));
         }

         else {
            product = productServicLocal.getProductById(Id.of(Integer.parseInt(idAsString), ProductId.class));
         }

         if (product.isPresent()) {
            response.reset();
            response.getOutputStream().write(product.get().getPhoto());
         }
      } catch (IOException |  NumberFormatException e) {
         Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
      }
   }
}

package ch.cheorges.onlineshop.web.purchase.jsf;

import ch.cheorges.onlineshop.common.product.dto.ProductId;
import ch.cheorges.onlineshop.common.purchase.dto.Purchase;
import ch.cheorges.onlineshop.common.purchase.service.ProductPurchaseServiceLocal;
import ch.cheorges.onlineshop.web.common.massage.MessageFactory;
import ch.cheorges.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.cheorges.onlineshop.web.customer.jsf.CustomerSessionJSF;
import ch.cheorges.onlineshop.web.product.dto.PersistPageProduct;
import ch.cheorges.onlineshop.web.purchase.dto.PageCartProduct;
import ch.cheorges.onlineshop.web.purchase.dto.PageShoppingCart;
import ch.cheorges.onlineshop.web.purchase.mapper.ProductPurchaseMapper;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductPurchaseJSF implements Serializable {

    @Inject
    private CustomerSessionJSF customerSessionJSF;

    @Inject
    private ProductPurchaseServiceLocal productPurchaseServiceLocal;

    @Inject
    private MessageFactory messageFactory;

    private PageShoppingCart shoppingCart = new PageShoppingCart();

    public List<PageCartProduct> getShoppingCart() {
        return shoppingCart.getPageCartProducts();
    }

    public Double getTotalAmount() {
        return shoppingCart.getTotalAmount();
    }

    public void addToShoppingCart(PersistPageProduct product) throws UnauthorizedAccessException {
        if (customerSessionJSF.getCustomerId().equals(product.getSeller().getId())) {
            messageFactory.showError("You can not buy your own product.");
            return;
        }
        shoppingCart.addPageCartProduct(product);
        messageFactory.showInfo(product.getTitle() + " added to shopping cart.");
    }

    public void removeFromShoppingCart(ProductId id) {
        if (shoppingCart.removePageCartProduct(id)) {
            messageFactory.showInfo("Product removed successful.");
        } else {
            messageFactory.showError("Product can not removed.");
        }
    }

    public void buyAllProductsInCart() throws UnauthorizedAccessException {
        Purchase purchase = ProductPurchaseMapper.INSTANCE.map(customerSessionJSF.getCustomer(), shoppingCart.getPageCartProducts());
        if (!productPurchaseServiceLocal.newPurchase(purchase).hasMessagesThenProvide(msg -> messageFactory.showError(msg))) {
            shoppingCart.clear();
            messageFactory.showInfo("Purchase completed successfully.");
        }
    }

    public void decrement(ProductId id) {
        shoppingCart.decrement(id);
    }

    public void increment(ProductId id) {
        shoppingCart.increment(id);
    }
}

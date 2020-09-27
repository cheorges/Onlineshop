package ch.zotteljedi.onlineshop.web.product.jsf;

import ch.zotteljedi.onlineshop.common.product.dto.ImmutablePurchase;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.common.product.dto.Purchase;
import ch.zotteljedi.onlineshop.common.product.service.ProductPurchaseLocal;
import ch.zotteljedi.onlineshop.web.common.massage.MessageFactory;
import ch.zotteljedi.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.web.customer.jsf.CustomerSessionJSF;
import ch.zotteljedi.onlineshop.web.product.dto.PageCartProduct;
import ch.zotteljedi.onlineshop.web.product.dto.PageShoppingCart;
import ch.zotteljedi.onlineshop.web.product.dto.PersistPageProduct;
import ch.zotteljedi.onlineshop.web.product.mapper.ProductPurchaseMapper;

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
    private ProductPurchaseLocal productPurchaseLocal;

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
        productPurchaseLocal.newPurchase(purchase);
    }

    public void decrement(ProductId id) {
        shoppingCart.decrement(id);
    }

    public void increment(ProductId id) {
        shoppingCart.increment(id);
    }
}

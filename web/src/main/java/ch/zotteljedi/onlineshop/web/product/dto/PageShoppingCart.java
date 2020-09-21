package ch.zotteljedi.onlineshop.web.product.dto;

import ch.zotteljedi.onlineshop.common.product.dto.ProductId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PageShoppingCart implements Serializable {
    private List<PageCartProduct> pageCartProducts = new ArrayList<>();

    public void addPageCartProduct(PersistPageProduct persistPageProduct) {
        isProductInShoppingCart(persistPageProduct.getId())
                .ifPresentOrElse(PageCartProduct::incrementUnit, () -> pageCartProducts.add(
                        new PageCartProduct(persistPageProduct.getId(),
                              persistPageProduct.getTitle(),
                              persistPageProduct.getUnitprice(),
                              persistPageProduct.getStock())));
    }

    public boolean removePageCartProduct(ProductId id) {
        Optional<PageCartProduct> productInShoppingCart = isProductInShoppingCart(id);
        if (productInShoppingCart.isPresent()) {
            pageCartProducts.remove(productInShoppingCart.get());
            return true;
        } else {
            return false;
        }
    }

    public void increment(ProductId id) {
        isProductInShoppingCart(id).ifPresent(product -> product.incrementUnit());
    }

    public void decrement(ProductId id) {
        isProductInShoppingCart(id).ifPresent(product -> product.decrementUnit());
    }

    public List<PageCartProduct> getPageCartProducts() {
        return pageCartProducts;
    }

    private Optional<PageCartProduct> isProductInShoppingCart(ProductId id) {
        return pageCartProducts.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

}

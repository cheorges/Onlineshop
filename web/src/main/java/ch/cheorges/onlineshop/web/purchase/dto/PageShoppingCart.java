package ch.cheorges.onlineshop.web.purchase.dto;

import ch.cheorges.onlineshop.common.product.dto.ProductId;
import ch.cheorges.onlineshop.web.product.dto.PersistPageProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PageShoppingCart implements Serializable {
    private final List<PageCartProduct> pageCartProducts = new ArrayList<>();

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

    public Double getTotalAmount() {
        return pageCartProducts.stream().mapToDouble(PageCartProduct::getTotalPrice).sum();
    }

    public List<PageCartProduct> getPageCartProducts() {
        return pageCartProducts;
    }

    public void clear() {
        pageCartProducts.clear();
    }

    private Optional<PageCartProduct> isProductInShoppingCart(ProductId id) {
        return pageCartProducts.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

}

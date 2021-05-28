package ch.cheorges.onlineshop.web.purchase.jsf;

import ch.cheorges.onlineshop.common.dto.Id;
import ch.cheorges.onlineshop.common.purchase.dto.PurchaseId;
import ch.cheorges.onlineshop.common.purchase.dto.PurchaseOverview;
import ch.cheorges.onlineshop.common.purchase.service.PurchaseServiceLocal;
import ch.cheorges.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.cheorges.onlineshop.web.customer.jsf.CustomerSessionJSF;
import ch.cheorges.onlineshop.web.purchase.dto.PagePurchase;
import ch.cheorges.onlineshop.web.purchase.dto.PagePurchaseItem;
import ch.cheorges.onlineshop.web.purchase.mapper.PurchaseMapper;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Named
@SessionScoped
public class PurchaseJSF implements Serializable {

    @Inject
    private PurchaseServiceLocal purchaseServiceLocal;

    @Inject
    private CustomerSessionJSF customerSessionJSF;

    private PagePurchase pagePurchase = new PagePurchase();
    private int purchaseId;

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
        refreshPagePurchase(purchaseId);
    }

    public PagePurchase getPurchase() {
        return pagePurchase;
    }

    public List<PagePurchaseItem> getPurchaseItems() {
        if (Objects.nonNull(pagePurchase)) {
            return pagePurchase.getItems();
        }
        return Collections.emptyList();
    }

    public List<PagePurchase> getPurchaseByCustomer() throws UnauthorizedAccessException {
        List<PurchaseOverview> purchaseByCustomer = purchaseServiceLocal.getPurchaseByCustomer(customerSessionJSF.getCustomerId());
        return PurchaseMapper.INSTANCE.map(purchaseByCustomer);
    }

    public void refreshPagePurchase(int id) {
        purchaseServiceLocal.getPurchaseById(Id.of(id, PurchaseId.class)).ifPresent(
                purchaseOverview -> pagePurchase = PurchaseMapper.INSTANCE.map(purchaseOverview));
    }

}

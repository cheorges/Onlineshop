package ch.zotteljedi.onlineshop.web.purchase.jsf;

import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.purchase.dto.PurchaseId;
import ch.zotteljedi.onlineshop.common.purchase.dto.PurchaseOverview;
import ch.zotteljedi.onlineshop.common.purchase.service.PurchaseServiceLocal;
import ch.zotteljedi.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.web.customer.jsf.CustomerSessionJSF;
import ch.zotteljedi.onlineshop.web.purchase.dto.PagePurchase;
import ch.zotteljedi.onlineshop.web.purchase.dto.PagePurchaseItem;
import ch.zotteljedi.onlineshop.web.purchase.mapper.PurchaseMapper;

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

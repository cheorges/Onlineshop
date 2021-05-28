package ch.cheorges.onlineshop.web.sale.jsf;

import ch.cheorges.onlineshop.web.sale.dto.PageSales;
import ch.cheorges.onlineshop.web.sale.dto.PageSalesItem;
import ch.cheorges.onlineshop.web.sale.mapper.SalesMapper;
import ch.cheorges.onlineshop.common.dto.Id;
import ch.cheorges.onlineshop.common.product.dto.ProductId;
import ch.cheorges.onlineshop.common.sale.dto.SalesOverview;
import ch.cheorges.onlineshop.common.sale.service.SalesServiceLocal;
import ch.cheorges.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.cheorges.onlineshop.web.customer.jsf.CustomerSessionJSF;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Named
@SessionScoped
public class SalesJSF implements Serializable {

    @Inject
    private SalesServiceLocal salesServiceLocal;

    @Inject
    private CustomerSessionJSF customerSessionJSF;

    private PageSales pageSales = new PageSales();
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
        refreshPageSales(productId);
    }

    public PageSales getPageSales() {
        return pageSales;
    }

    public List<PageSalesItem> getSalesItems() {
        if (Objects.nonNull(pageSales)) {
            return pageSales.getItems();
        }
        return Collections.emptyList();
    }

    public List<PageSales> getSalesByCustomer() throws UnauthorizedAccessException {
        List<SalesOverview> localSalesByCustomer = salesServiceLocal.getSalesByCustomer(customerSessionJSF.getCustomerId());
        return SalesMapper.INSTANCE.map(localSalesByCustomer);
    }

    public void refreshPageSales(int id) {
        salesServiceLocal.getSalesByProductId(Id.of(id, ProductId.class)).ifPresent(
                salesOverview -> pageSales = SalesMapper.INSTANCE.map(salesOverview));
    }

}

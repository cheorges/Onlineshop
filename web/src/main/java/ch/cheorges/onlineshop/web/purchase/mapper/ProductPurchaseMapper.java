package ch.cheorges.onlineshop.web.purchase.mapper;

import ch.cheorges.onlineshop.common.purchase.dto.ImmutableCartProduct;
import ch.cheorges.onlineshop.common.purchase.dto.ImmutablePurchase;
import ch.cheorges.onlineshop.web.customer.dto.PageCustomer;
import ch.cheorges.onlineshop.web.purchase.dto.PageCartProduct;
import ch.cheorges.onlineshop.common.purchase.dto.ImmutableCartProduct;
import ch.cheorges.onlineshop.common.purchase.dto.ImmutablePurchase;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductPurchaseMapper {
    ProductPurchaseMapper INSTANCE = Mappers.getMapper(ProductPurchaseMapper.class);

    @Mapping(target = "buyerId", source = "customer.id")
    @Mapping(target = "cartProduct", source = "pageCartProducts")
    ImmutablePurchase map(PageCustomer customer, List<PageCartProduct> pageCartProducts);

    @Mapping(target = "productId", source = "id")
    ImmutableCartProduct map(PageCartProduct pageCartProduct);

    List<ImmutableCartProduct> map(List<PageCartProduct> pageCartProducts);
}

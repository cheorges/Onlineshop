package ch.zotteljedi.onlineshop.web.purchase.mapper;

import ch.zotteljedi.onlineshop.common.purchase.dto.PurchaseOverview;
import ch.zotteljedi.onlineshop.web.purchase.dto.PagePurchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = PurchaseItemMapper.class)
public interface PurchaseMapper {
    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    @Mapping(target = "purchaseId", source = "id")
    @Mapping(target = "items", source = "purchaseItems")
    PagePurchase map(PurchaseOverview purchaseOverview);

    List<PagePurchase> map(List<PurchaseOverview> purchaseOverviews);

}

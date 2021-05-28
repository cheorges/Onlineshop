package ch.cheorges.onlineshop.web.purchase.mapper;

import ch.cheorges.onlineshop.common.purchase.dto.PurchaseItemOverview;
import ch.cheorges.onlineshop.web.purchase.dto.PagePurchaseItem;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseItemMapper {
    PurchaseItemMapper INSTANCE = Mappers.getMapper(PurchaseItemMapper.class);

    PagePurchaseItem map(PurchaseItemOverview purchaseItemOverview);

    List<PagePurchaseItem> map(List<PurchaseItemOverview> purchaseItemOverviews);
}

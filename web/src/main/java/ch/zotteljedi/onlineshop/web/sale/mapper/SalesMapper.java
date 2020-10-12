package ch.zotteljedi.onlineshop.web.sale.mapper;

import ch.zotteljedi.onlineshop.common.sale.dto.SalesOverview;
import ch.zotteljedi.onlineshop.web.sale.dto.PageSales;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = SalesItemMapper.class)
public interface SalesMapper {
    SalesMapper INSTANCE = Mappers.getMapper(SalesMapper.class);

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "items", source = "salesItems")
    PageSales map(SalesOverview purchaseOverview);

    List<PageSales> map(List<SalesOverview> purchaseOverviews);

}

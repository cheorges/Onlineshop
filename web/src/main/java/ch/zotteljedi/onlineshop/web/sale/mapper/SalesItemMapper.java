package ch.zotteljedi.onlineshop.web.sale.mapper;

import ch.zotteljedi.onlineshop.common.sale.dto.SalesItemOverview;
import ch.zotteljedi.onlineshop.web.sale.dto.PageSalesItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SalesItemMapper {
    SalesItemMapper INSTANCE = Mappers.getMapper(SalesItemMapper.class);

    PageSalesItem map(SalesItemOverview salesItemOverview);

    List<PageSalesItem> map(List<SalesItemOverview> salesItemOverview);
}

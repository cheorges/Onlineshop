package ch.zotteljedi.onlineshop.core.sale.mapper;

import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.common.sale.dto.ImmutableSalesItemOverview;
import ch.zotteljedi.onlineshop.common.sale.dto.ImmutableSalesOverview;
import ch.zotteljedi.onlineshop.common.sale.dto.SalesItemOverview;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SalesMapper {

    SalesMapper INSTANCE = Mappers.getMapper(SalesMapper.class);

    @Mapping(target = "id", source = "productEntity.id", qualifiedByName = "id")
    @Mapping(target = "salesItems", source = "salesItemOverviews")
    @Mapping(target = "title", source = "productEntity.title")
    @Mapping(target = "from", ignore = true)
    ImmutableSalesOverview map(ProductEntity productEntity, List<SalesItemOverview> salesItemOverviews);

    @Mapping(target = "buyerRepresentation", source = "representation")
    @Mapping(target = "unit", source = "purchaseItemEntity.unit")
    @Mapping(target = "unitprice", source = "purchaseItemEntity.unitprice")
    @Mapping(target = "boughtAt", source = "purchaseItemEntity.purchase.boughtAt")
    @Mapping(target = "from", ignore = true)
    ImmutableSalesItemOverview map(PurchaseItemEntity purchaseItemEntity, String representation);

    @Named("id")
    default ProductId map(final Integer id) {
        return Id.of(id, ProductId.class);
    }
}

package ch.cheorges.onlineshop.core.purchase.mapper;

import ch.cheorges.onlineshop.common.dto.Id;
import ch.cheorges.onlineshop.common.purchase.dto.CartProduct;
import ch.cheorges.onlineshop.common.purchase.dto.ImmutablePurchaseItemOverview;
import ch.cheorges.onlineshop.common.purchase.dto.ImmutablePurchaseOverview;
import ch.cheorges.onlineshop.common.purchase.dto.PurchaseId;
import ch.cheorges.onlineshop.common.purchase.dto.PurchaseItemOverview;
import ch.cheorges.onlineshop.core.customer.mapper.CustomerMapper;
import ch.cheorges.onlineshop.data.entity.PurchaseEntity;
import ch.cheorges.onlineshop.data.entity.PurchaseItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = CustomerMapper.class)
public interface PurchaseMapper {

    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "purchase", ignore = true)
    @Mapping(target = "product", ignore = true)
    PurchaseItemEntity map(CartProduct cartProduct);

    @Mapping(target = "title", source = "purchaseItemEntity.product.title")
    @Mapping(target = "sellerRepresentation", source = "representation")
    @Mapping(target = "unit", source = "purchaseItemEntity.unit")
    @Mapping(target = "unitprice", source = "purchaseItemEntity.unitprice")
    @Mapping(target = "from", ignore = true)
    ImmutablePurchaseItemOverview map(PurchaseItemEntity purchaseItemEntity, String representation);

    @Mapping(target = "purchaseItems", source = "purchaseItemOverviews")
    @Mapping(target = "id", source = "purchaseEntity.id", qualifiedByName = "id")
    @Mapping(target = "boughtAt", source = "purchaseEntity.boughtAt")
    @Mapping(target = "from", ignore = true)
    ImmutablePurchaseOverview map(PurchaseEntity purchaseEntity, List<PurchaseItemOverview> purchaseItemOverviews);

    @Named("id")
    default PurchaseId map(final Integer id) {
        return Id.of(id, PurchaseId.class);
    }
}

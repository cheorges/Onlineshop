package ch.zotteljedi.onlineshop.core.product.mapper;

import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.product.dto.ChangeProduct;
import ch.zotteljedi.onlineshop.common.product.dto.ImmutableProduct;
import ch.zotteljedi.onlineshop.common.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.core.customer.mapper.CustomerMapper;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {CustomerMapper.class})
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "purchaseItemEntities", ignore = true)
    ProductEntity map(NewProduct product);

    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "id", source = "id", qualifiedByName = "mapIdTo")
    @Mapping(target = "purchaseItemEntities", ignore = true)
    ProductEntity map(ChangeProduct product);

    @Mapping(target = "from", ignore = true)
    @Mapping(target = "id", source = "id", qualifiedByName = "mapIdTo")
    ImmutableProduct map(ProductEntity product);

    List<ImmutableProduct> map(List<ProductEntity> products);

    @Named("mapIdTo")
    default ProductId map(Integer id) {
        return Id.of(id, ProductId.class);
    }

    @Named("mapIdTo")
    default Integer map(ProductId productId) {
        return productId.getValue();
    }
}

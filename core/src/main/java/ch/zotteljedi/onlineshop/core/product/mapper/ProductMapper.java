package ch.zotteljedi.onlineshop.core.product.mapper;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.product.dto.*;
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
    ProductEntity map(NewProduct product);

    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "id", source = "id", qualifiedByName = "mapIdTo")
    ProductEntity map(ChangeProduct product);

    @Mapping(target = "from", ignore = true)
    @Mapping(target = "id", source = "id", qualifiedByName = "mapIdTo")
    ImmutableProduct map(ProductEntity product);

    List<ImmutableProduct> map(List<ProductEntity> products);

    @Named("mapIdTo")
    default ProductId map(Integer id) {
        return ProductId.of(id, ProductId.class);
    }

    @Named("mapIdTo")
    default Integer map(ProductId productId) {
        return productId.getValue();
    }
}

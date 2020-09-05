package ch.zotteljedi.onlineshop.product.dto.mapper;

import ch.zotteljedi.onlineshop.customer.dto.mapper.CustomerMapper;
import ch.zotteljedi.onlineshop.entity.ProductEntity;
import ch.zotteljedi.onlineshop.product.dto.ImmutableProduct;
import ch.zotteljedi.onlineshop.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.product.dto.ProductId;
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

    @Mapping(target = "from", ignore = true)
    @Mapping(target = "id", source = "id", qualifiedByName = "mapIdTo")
    ImmutableProduct map(ProductEntity product);

    List<ImmutableProduct> map(List<ProductEntity> products);

    @Named("mapIdTo")
    default ProductId map(Integer id) {
        return ProductId.of(id, ProductId.class);
    }
}

package ch.zotteljedi.onlineshop.core.product.mapper;

import ch.zotteljedi.onlineshop.common.product.dto.CartProduct;
import ch.zotteljedi.onlineshop.core.customer.mapper.CustomerMapper;
import ch.zotteljedi.onlineshop.data.entity.PurchaseItemEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {CustomerMapper.class})
public interface PurchaseMapper {

   PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "purchase", ignore = true)
   @Mapping(target = "product", ignore = true)
   PurchaseItemEntity map(CartProduct cartProduct);

}

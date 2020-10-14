package ch.zotteljedi.onlineshop.core.customer.mapper;

import ch.zotteljedi.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.customer.dto.ImmutableCustomer;
import ch.zotteljedi.onlineshop.common.customer.dto.NewCustomer;
import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    CustomerEntity map(NewCustomer customer);

    @Mapping(target = "id", source = "id", qualifiedByName = "mapIdTo")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    CustomerEntity map(Customer customer);

    @Mapping(target = "from", ignore = true)
    @Mapping(target = "id", source = "id", qualifiedByName = "mapIdTo")
    ImmutableCustomer map(CustomerEntity customerEntity);

    @Named("mapIdTo")
    default CustomerId map(Integer id) {
        return CustomerId.of(id, CustomerId.class);
    }

    @Named("mapIdTo")
    default Integer map(CustomerId customerId) {
        return customerId.getValue();
    }

}

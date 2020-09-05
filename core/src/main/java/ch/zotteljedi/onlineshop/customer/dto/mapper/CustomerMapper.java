package ch.zotteljedi.onlineshop.customer.dto.mapper;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.customer.dto.ImmutableCustomer;
import ch.zotteljedi.onlineshop.customer.dto.NewCustomer;
import ch.zotteljedi.onlineshop.entity.CustomerEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "id", ignore = true)
    CustomerEntity map(NewCustomer customer);

    @Mapping(target = "id", source = "id", qualifiedByName = "mapIdTo")
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

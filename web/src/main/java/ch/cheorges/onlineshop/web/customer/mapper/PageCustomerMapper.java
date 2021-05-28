package ch.cheorges.onlineshop.web.customer.mapper;

import ch.cheorges.onlineshop.common.customer.dto.Customer;
import ch.cheorges.onlineshop.web.customer.dto.PageCustomer;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PageCustomerMapper {
    PageCustomerMapper INSTANCE = Mappers.getMapper(PageCustomerMapper.class);

    PageCustomer map(Customer customer, @MappingTarget PageCustomer pageCustomer);

    default PageCustomer map(final Customer customer) {
        return map(customer, new PageCustomer(customer.getId()));
    }

}

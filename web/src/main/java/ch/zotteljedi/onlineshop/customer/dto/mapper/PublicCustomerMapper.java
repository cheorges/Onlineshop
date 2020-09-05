package ch.zotteljedi.onlineshop.customer.dto.mapper;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.PublicCustomer;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PublicCustomerMapper {
   PublicCustomerMapper INSTANCE = Mappers.getMapper(PublicCustomerMapper.class);

   PublicCustomer map(Customer customer, @MappingTarget PublicCustomer publicCustomer);

   default PublicCustomer map(final Customer customer) {
      return map(customer, new PublicCustomer(customer.getId()));
   }

}

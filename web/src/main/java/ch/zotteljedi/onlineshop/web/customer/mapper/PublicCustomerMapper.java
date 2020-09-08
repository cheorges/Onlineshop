package ch.zotteljedi.onlineshop.web.customer.mapper;

import ch.zotteljedi.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.web.customer.dto.PersistPageCustomer;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PublicCustomerMapper {
   PublicCustomerMapper INSTANCE = Mappers.getMapper(PublicCustomerMapper.class);

   PersistPageCustomer map(Customer customer, @MappingTarget PersistPageCustomer changeCustomer);

   default PersistPageCustomer map(final Customer customer) {
      return map(customer, new PersistPageCustomer(customer.getId()));
   }

}

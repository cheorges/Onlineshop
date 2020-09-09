package ch.zotteljedi.onlineshop.web.customer.mapper;

import ch.zotteljedi.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.web.customer.dto.PageCustomer;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-09T07:51:52+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.8 (AdoptOpenJDK)"
)
public class PublicCustomerMapperImpl implements PublicCustomerMapper {

    @Override
    public PageCustomer map(Customer customer, PageCustomer changeCustomer) {
        if ( customer == null ) {
            return null;
        }

        changeCustomer.setUsername( customer.getUsername() );
        changeCustomer.setFirstname( customer.getFirstname() );
        changeCustomer.setLastname( customer.getLastname() );
        changeCustomer.setEmail( customer.getEmail() );

        return changeCustomer;
    }
}

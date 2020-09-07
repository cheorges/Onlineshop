package ch.zotteljedi.onlineshop.customer.mapper;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.PublicCustomer;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-07T17:38:10+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.8 (AdoptOpenJDK)"
)
public class PublicCustomerMapperImpl implements PublicCustomerMapper {

    @Override
    public PublicCustomer map(Customer customer, PublicCustomer publicCustomer) {
        if ( customer == null ) {
            return null;
        }

        publicCustomer.setUsername( customer.getUsername() );
        publicCustomer.setFirstname( customer.getFirstname() );
        publicCustomer.setLastname( customer.getLastname() );
        publicCustomer.setEmail( customer.getEmail() );

        return publicCustomer;
    }
}

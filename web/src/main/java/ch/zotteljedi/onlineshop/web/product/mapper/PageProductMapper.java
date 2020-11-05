package ch.zotteljedi.onlineshop.web.product.mapper;

import ch.zotteljedi.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.common.product.dto.Product;
import ch.zotteljedi.onlineshop.web.customer.dto.PageCustomer;
import ch.zotteljedi.onlineshop.web.customer.mapper.PageCustomerMapper;
import ch.zotteljedi.onlineshop.web.product.dto.PersistPageProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PageProductMapper {

    PageProductMapper INSTANCE = Mappers.getMapper(PageProductMapper.class);

    @Mapping(target = "seller", source = "seller", qualifiedByName = "mapSeller")
    PersistPageProduct map(Product product, @MappingTarget PersistPageProduct pageProduct);

    List<PersistPageProduct> map(List<Product> products);

    default PersistPageProduct map(final Product product) {
        return map(product, new PersistPageProduct(product.getId()));
    }

    default PageCustomer mapSeller(Customer customer) {
        return PageCustomerMapper.INSTANCE.map(customer);
    }
}

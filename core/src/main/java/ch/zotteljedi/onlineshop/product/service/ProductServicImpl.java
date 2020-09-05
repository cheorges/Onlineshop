package ch.zotteljedi.onlineshop.product.service;

import ch.zotteljedi.onlineshop.common.dto.MessageContainer;
import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.customer.dto.mapper.CustomerMapper;
import ch.zotteljedi.onlineshop.customer.service.CustomerServiceLocal;
import ch.zotteljedi.onlineshop.entity.CustomerEntity;
import ch.zotteljedi.onlineshop.entity.ProductEntity;
import ch.zotteljedi.onlineshop.helper.ApplicationService;
import ch.zotteljedi.onlineshop.helper.message.CustomerByIdNotFound;
import ch.zotteljedi.onlineshop.helper.message.CustomerUsernameAllreadyExist;
import ch.zotteljedi.onlineshop.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.product.dto.Product;
import ch.zotteljedi.onlineshop.product.dto.mapper.ProductMapper;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Local(ProductServicLocal.class)
@Transactional
public class ProductServicImpl extends ApplicationService implements ProductServicLocal {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CustomerServiceLocal customerServiceLocal;

    @Override
    public List<Product> getProductsBySeller(CustomerId id) {
        List<ProductEntity> productEntities = em.createNamedQuery("ProductEntity.getBySeller", ProductEntity.class)
                .setParameter("seller", customerServiceLocal.getCustomerById(id).get())
                .getResultList();
        return ProductMapper.INSTANCE.map(productEntities).stream().map(it -> (Product) it).collect(Collectors.toList());
    }

    @Override
    public MessageContainer addNewProduct(NewProduct product) {
        Optional<Customer> seller = customerServiceLocal.getCustomerById(product.getCustomerId());
        if (seller.isPresent()) {
            ProductEntity productEntity = ProductMapper.INSTANCE.map(product);
            productEntity.setSeller(CustomerMapper.INSTANCE.map(seller.get()));
            em.persist(productEntity);
        } else {
            addMessage(new CustomerByIdNotFound(product.getCustomerId()));
        }
        return getMessageContainer();
    }
}

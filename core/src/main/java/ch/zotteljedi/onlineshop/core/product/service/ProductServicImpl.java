package ch.zotteljedi.onlineshop.core.product.service;

import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.product.dto.ChangeProduct;
import ch.zotteljedi.onlineshop.common.product.service.ProductServicLocal;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;
import ch.zotteljedi.onlineshop.common.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.common.product.dto.Product;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.core.product.mapper.ProductMapper;

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
    private CustomerServiceImpl customerService;

    @Override
    public List<Product> getProductsBySeller(CustomerId id) {
        List<ProductEntity> productEntities = em.createNamedQuery("ProductEntity.getBySeller", ProductEntity.class)
                .setParameter("seller", customerService.getCustomerEntityById(id))
                .getResultList();
        return ProductMapper.INSTANCE.map(productEntities).stream().map(it -> (Product) it).collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> products = em.createNamedQuery("ProductEntity.get", ProductEntity.class).getResultList();
        return ProductMapper.INSTANCE.map(products).stream().map(it -> (Product) it).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getProductById(ProductId id) {
        return em.createNamedQuery("ProductEntity.getById", ProductEntity.class)
                .setParameter("id", id.getValue())
                .getResultList()
                .stream()
                .findFirst()
                .map(ProductMapper.INSTANCE::map);
    }

    @Override
    public MessageContainer addNewProduct(NewProduct product) {
        ProductEntity productEntity = ProductMapper.INSTANCE.map(product);
        productEntity.setSeller(customerService.getCustomerEntityById(product.getSellerId()));
        em.persist(productEntity);
        return getMessageContainer();
    }

    @Override
    public MessageContainer changeProduct(ChangeProduct product) {
        ProductEntity productEntity = ProductMapper.INSTANCE.map(product);
        productEntity.setSeller(customerService.getCustomerEntityById(product.getSellerId()));
        em.merge(productEntity);
        return getMessageContainer();
    }

}

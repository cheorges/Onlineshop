package ch.zotteljedi.onlineshop.core.product.service;

import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.product.dto.ChangeProduct;
import ch.zotteljedi.onlineshop.common.product.service.ProductServicLocal;
import ch.zotteljedi.onlineshop.core.customer.message.CustomerByIdNotFound;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.core.product.message.NotEnoughProductsAvailable;
import ch.zotteljedi.onlineshop.core.product.message.ProductByIdNotFound;
import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;
import ch.zotteljedi.onlineshop.common.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.common.product.dto.Product;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.core.product.mapper.ProductMapper;
import edu.emory.mathcs.backport.java.util.Collections;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Local(ProductServicLocal.class)
@Transactional
public class ProductServicImpl extends ApplicationService implements ProductServicLocal {

    @PersistenceContext(unitName = "ZotteltecPersistenceProvider")
    private EntityManager em;

    @Inject
    private CustomerServiceImpl customerService;

    @Override
    public List<Product> getProductsBySeller(CustomerId id) {
        Optional<CustomerEntity> customer = customerService.getCustomerEntityById(id);
        if (customer.isPresent()) {
            List<ProductEntity> productEntities = em.createNamedQuery("ProductEntity.getBySeller", ProductEntity.class)
                  .setParameter("seller", customer.get())
                  .getResultList();
            return ProductMapper.INSTANCE.map(productEntities).stream().map(it -> (Product) it).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Product> getAllAvailableProducts() {
        List<ProductEntity> products = em.createNamedQuery("ProductEntity.getByStockNotEmpty", ProductEntity.class).getResultList();
        return ProductMapper.INSTANCE.map(products).stream().map(it -> (Product) it).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getProductById(ProductId id) {
        return getProductEntityById(id).map(ProductMapper.INSTANCE::map);
    }

    @Override
    public MessageContainer addNewProduct(NewProduct product) {
        ProductEntity productEntity = ProductMapper.INSTANCE.map(product);
        customerService.getCustomerEntityById(product.getSellerId())
              .ifPresentOrElse(productEntity::setSeller, () -> addMessage(new CustomerByIdNotFound(product.getSellerId())));
        em.persist(productEntity);
        return getMessageContainer();
    }

    @Override
    public MessageContainer changeProduct(ChangeProduct product) {
        ProductEntity productEntity = ProductMapper.INSTANCE.map(product);
        customerService.getCustomerEntityById(product.getSellerId())
              .ifPresentOrElse(productEntity::setSeller, () -> addMessage(new CustomerByIdNotFound(product.getSellerId())));
        em.merge(productEntity);
        return getMessageContainer();
    }

    @Override
    public MessageContainer deleteProduct(ProductId id) {
        getProductEntityById(id).ifPresentOrElse(product -> em.remove(product), () -> addMessage(new ProductByIdNotFound(id)));
        return getMessageContainer();
    }

    public Optional<ProductEntity> getProductEntityById(ProductId id) {
        return em.createNamedQuery("ProductEntity.getById", ProductEntity.class)
                .setParameter("id", id.getValue())
                .getResultList()
                .stream()
                .findFirst();
    }

    public MessageContainer removeStockByProductId(ProductId id, Integer unit) {
        getProductEntityById(id).ifPresentOrElse(product -> {
            Integer currentStock = product.getStock();
            if (currentStock >= unit) {
                product.setStock(currentStock - unit);
                em.persist(product);
            } else {
                addMessage(new NotEnoughProductsAvailable(currentStock));
            }
        }, () -> addMessage(new ProductByIdNotFound(id)));
        return getMessageContainer();
    }

}

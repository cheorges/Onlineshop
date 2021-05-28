package ch.cheorges.onlineshop.data.entity;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PurchaseItemEntityValidatorTest {

    private Validator validator;
    private PurchaseItemEntity purchaseItemEntity;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        purchaseItemEntity = new PurchaseItemEntity();
        purchaseItemEntity.setId(1);
        purchaseItemEntity.setUnit(2);
        purchaseItemEntity.setUnitprice(3.4);
        purchaseItemEntity.setProduct(mock(ProductEntity.class));
        purchaseItemEntity.setPurchase(mock(PurchaseEntity.class));
    }

    @Test
    public void test_valid_purchase() {
        // When
        Set<ConstraintViolation<PurchaseItemEntity>> constraintViolations = validator.validate(purchaseItemEntity);

        // Then
        assertThat(constraintViolations.size(), is(0));
    }

    @Test
    public void test_no_unit() {
        // Given
        purchaseItemEntity.setUnit(null);

        // When
        Set<ConstraintViolation<PurchaseItemEntity>> constraintViolations = validator.validate(purchaseItemEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Unit may not be blank."));
    }

    @Test
    public void test_no_unitprice() {
        // Given
        purchaseItemEntity.setUnitprice(null);

        // When
        Set<ConstraintViolation<PurchaseItemEntity>> constraintViolations = validator.validate(purchaseItemEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Price may not be blank."));
    }

    @Test
    public void test_no_prduct() {
        // Given
        purchaseItemEntity.setProduct(null);

        // When
        Set<ConstraintViolation<PurchaseItemEntity>> constraintViolations = validator.validate(purchaseItemEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Product may not be blank."));
    }

    @Test
    public void test_no_purchase() {
        // Given
        purchaseItemEntity.setPurchase(null);

        // When
        Set<ConstraintViolation<PurchaseItemEntity>> constraintViolations = validator.validate(purchaseItemEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Purchase may not be blank."));
    }
}
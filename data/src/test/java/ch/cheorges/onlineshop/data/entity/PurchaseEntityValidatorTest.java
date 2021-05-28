package ch.cheorges.onlineshop.data.entity;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PurchaseEntityValidatorTest {

    private Validator validator;
    private PurchaseEntity purchaseEntity;
    private final LocalDate now = LocalDate.now();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(1);
        purchaseEntity.setBoughtAt(now);
        purchaseEntity.setBuyer(mock(CustomerEntity.class));
    }

    @Test
    public void test_valid_purchase() {
        // When
        Set<ConstraintViolation<PurchaseEntity>> constraintViolations = validator.validate(purchaseEntity);

        // Then
        assertThat(constraintViolations.size(), is(0));
    }

    @Test
    public void test_no_purchase_date() {
        // Given
        purchaseEntity.setBoughtAt(null);

        // When
        Set<ConstraintViolation<PurchaseEntity>> constraintViolations = validator.validate(purchaseEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Purchasedate may not be blank."));
    }

    @Test
    public void test_no_buyer() {
        // Given
        purchaseEntity.setBuyer(null);

        // When
        Set<ConstraintViolation<PurchaseEntity>> constraintViolations = validator.validate(purchaseEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Buyer may not be blank."));
    }

}
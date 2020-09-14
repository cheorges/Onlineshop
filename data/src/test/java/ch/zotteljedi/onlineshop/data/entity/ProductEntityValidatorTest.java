package ch.zotteljedi.onlineshop.data.entity;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ProductEntityValidatorTest {

    private Validator validator;
    private ProductEntity productEntity;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        productEntity = new ProductEntity();
        productEntity.setTitle("title");
        productEntity.setDescription("description");
        productEntity.setUnitprice(42.0);
        productEntity.setStock(5);
        productEntity.setPhoto(generateRandomByte(20));
        productEntity.setSeller(mock(CustomerEntity.class));
    }

    @Test
    public void test_valid_customer() {
        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        assertThat(constraintViolations.size(), is(0));
    }

    @Test
    public void test_no_title() {
        // Given
        productEntity.setTitle(null);

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Title may not be empty."));
    }

    @Test
    public void test_empty_title() {
        // Given
        productEntity.setTitle("");

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(2));
        assertTrue(messages.contains("Title may not be empty."));
        assertTrue(messages.contains("Title must be between 1 and 255 characters."));
    }

    @Test
    public void test_to_long_title() {
        // Given
        productEntity.setTitle(createStringForCharacter(260));

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Title must be between 1 and 255 characters."));
    }

    @Test
    public void test_no_description() {
        // Given
        productEntity.setDescription(null);

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(0));
    }

    @Test
    public void test_empty_description() {
        // Given
        productEntity.setDescription("");

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(0));
    }

    @Test
    public void test_to_long_description() {
        // Given
        productEntity.setDescription(createStringForCharacter(1001));

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Description should not be greater than 1000 characters."));
    }

    @Test
    public void test_no_price() {
        // Given
        productEntity.setUnitprice(null);

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Price may not be blank."));
    }

    @Test
    public void test_zero_price() {
        // Given
        productEntity.setUnitprice(0.0);

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(0));
    }

    @Test
    public void test_no_stock() {
        // Given
        productEntity.setStock(null);

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Stock may not be blank."));
    }

    @Test
    public void test_no_photo() {
        // Given
        productEntity.setPhoto(null);

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Photo may not be blank."));
    }

    @Test
    public void test_no_seller() {
        // Given
        productEntity.setSeller(null);

        // When
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Seller may not be blank."));
    }

    private byte[] generateRandomByte(int size) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }

    private String createStringForCharacter(int size) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int it = 0; it < size; it++) {
            builder.append((char)(random.nextInt(26) + 'a'));
        }
        return builder.toString();
    }

}
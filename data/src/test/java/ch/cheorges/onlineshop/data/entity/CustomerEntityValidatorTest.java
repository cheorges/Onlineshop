package ch.cheorges.onlineshop.data.entity;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CustomerEntityValidatorTest {

    private Validator validator;
    private CustomerEntity customerEntity;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        customerEntity = new CustomerEntity();
        customerEntity.setUsername("admin");
        customerEntity.setFirstname("Administrator");
        customerEntity.setLastname("cheorgestec");
        customerEntity.setEmail("admin@cheorgestec.ch");
        customerEntity.setPassword("password");
    }

    @Test
    public void test_valid_customer() {
        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        assertThat(constraintViolations.size(), is(0));
    }

    @Test
    public void test_no_username() {
        // Given
        customerEntity.setUsername(null);

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Username may not be empty."));
    }

    @Test
    public void test_empty_username() {
        // Given
        customerEntity.setUsername("");

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(2));
        assertTrue(messages.contains("Username may not be empty."));
        assertTrue(messages.contains("Username must be between 1 and 255 characters."));
    }

    @Test
    public void test_to_long_username() {
        // Given
        customerEntity.setUsername(createStringForCharacter(260));

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Username must be between 1 and 255 characters."));
    }

    @Test
    public void test_no_firstname() {
        // Given
        customerEntity.setFirstname(null);

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Firstname may not be empty."));
    }

    @Test
    public void test_empty_firstname() {
        // Given
        customerEntity.setFirstname("");

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(2));
        assertTrue(messages.contains("Firstname may not be empty."));
        assertTrue(messages.contains("Firstname must be between 1 and 255 characters."));
    }

    @Test
    public void test_to_long_firstname() {
        // Given
        customerEntity.setFirstname(createStringForCharacter(260));

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Firstname must be between 1 and 255 characters."));
    }

    @Test
    public void test_no_lastname() {
        // Given
        customerEntity.setLastname(null);

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Lastname may not be empty."));
    }

    @Test
    public void test_empty_lastname() {
        // Given
        customerEntity.setLastname("");

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(2));
        assertTrue(messages.contains("Lastname may not be empty."));
        assertTrue(messages.contains("Lastname must be between 1 and 255 characters."));
    }

    @Test
    public void test_to_long_lastname() {
        // Given
        customerEntity.setLastname(createStringForCharacter(260));

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Lastname must be between 1 and 255 characters."));
    }

    @Test
    public void test_no_valid_email() {
        // Given
        customerEntity.setEmail("no valid email");

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Is not a valid email."));
    }

    @Test
    public void test_email_to_long() {
        // Given
        customerEntity.setEmail(createStringForCharacter(260));

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(2));
        assertTrue(messages.contains("Is not a valid email."));
        assertTrue(messages.contains("Email must be between 1 and 255 characters."));
    }

    @Test
    public void test_no_email() {
        // Given
        customerEntity.setEmail(null);

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Email may not be empty."));
    }

    @Test
    public void test_empty_email() {
        // Given
        customerEntity.setEmail("");

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(2));
        assertTrue(messages.contains("Email may not be empty."));
        assertTrue(messages.contains("Email must be between 1 and 255 characters."));
    }

    @Test
    public void test_no_password() {
        // Given
        customerEntity.setPassword(null);

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Password may not be empty."));
    }

    @Test
    public void test_empty_password() {
        // Given
        customerEntity.setPassword("");

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(2));
        assertTrue(messages.contains("Password may not be empty."));
        assertTrue(messages.contains("Password must be between 6 and 255 characters."));
    }

    @Test
    public void test_to_long_password() {
        // Given
        customerEntity.setPassword(createStringForCharacter(260));

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Password must be between 6 and 255 characters."));
    }

    @Test
    public void test_to_short_password() {
        // Given
        customerEntity.setPassword(createStringForCharacter(5));

        // When
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        // Then
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(messages.size(), is(1));
        assertTrue(messages.contains("Password must be between 6 and 255 characters."));
    }

    private String createStringForCharacter(int size) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int it = 0; it < size; it++) {
            builder.append((char) (random.nextInt(26) + 'a'));
        }
        return builder.toString();
    }

}
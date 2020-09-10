package ch.zotteljedi.onlineshop.data.entity;

import org.junit.BeforeClass;
import org.junit.Test;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CustomerEntityTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void manufacturerIsNull() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail("no valid email");

        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

        assertEquals( 1, constraintViolations.size() );
        assertEquals("Is not a valid email.", constraintViolations.iterator().next().getMessage()
        );
    }


}
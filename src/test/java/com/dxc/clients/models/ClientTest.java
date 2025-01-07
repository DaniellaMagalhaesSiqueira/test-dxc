package com.dxc.clients.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.dxc.clients.config.ValidationConfig;


@SpringBootTest
@Import(ValidationConfig.class)
class ClientTest {

	 private Client validClient;
	    private Client invalidClient;

	    @Autowired
	    private LocalValidatorFactoryBean validator;

	    @BeforeEach
	    public void setUp() {
	    	validClient = new Client();
	        validClient.setFirstName("Jane");
	        validClient.setLastName("Do");
	        validClient.setBirthDate(LocalDate.of(1980, 10, 01));
	        validClient.setEmail("janedo@mail.com");
	        validClient.setCpf("439.759.637-94");

	        invalidClient = new Client();
	        invalidClient.setFirstName(null);
	        invalidClient.setLastName("");
	        invalidClient.setBirthDate(LocalDate.now().plusDays(1));
	        invalidClient.setEmail("janedo@mail");
	        invalidClient.setCpf("43975963794");

	    }

	    @Test
	    public void testClient() {

	        assertEquals("Jane", validClient.getFirstName());
	        assertEquals("Do", validClient.getLastName());
	        assertEquals(LocalDate.of(1980, 10, 01), validClient.getBirthDate());
	        assertEquals("janedo@mail.com", validClient.getEmail());
	        assertEquals("439.759.637-94", validClient.getCpf());

	    }

	    @Test
	    public void testValidationName(){

	        Errors errors = new BeanPropertyBindingResult(invalidClient, "clientInvalid");
	        validator.validate(invalidClient, errors);
	        assertTrue(errors.hasFieldErrors("firstName"));
	        assertTrue(errors.hasFieldErrors("lastName"));

	    }
	    @Test
	    public void testValidationCPF(){
	    	
	    	Errors errors = new BeanPropertyBindingResult(invalidClient, "clientInvalid");
	    	validator.validate(invalidClient, errors);
	    	assertTrue(errors.hasFieldErrors("cpf"));
	    	
	    }
	    @Test
	    public void testValidationEmail(){
	    	
	    	Errors errors = new BeanPropertyBindingResult(invalidClient, "clientInvalid");
	    	validator.validate(invalidClient, errors);
	    	assertTrue(errors.hasFieldErrors("email"));
	    	
	    }
	    @Test
	    public void testValidationBirthDate(){
	    	
	    	Errors errors = new BeanPropertyBindingResult(invalidClient, "clientInvalid");
	    	validator.validate(invalidClient, errors);
	    	assertTrue(errors.hasFieldErrors("birthDate"));
	    	
	    }

}

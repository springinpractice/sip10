/* 
 * Copyright (c) 2013 Manning Publications Co.
 * 
 * Book: http://manning.com/wheeler/
 * Blog: http://springinpractice.com/
 * Code: https://github.com/springinpractice
 */
package com.springinpractice.ch10.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.springinpractice.ch10.model.Contact;
import com.springinpractice.web.ResourceNotFoundException;
//import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

// NOTE: SimpleJdbcTemplate is deprecated as of Spring 3.1. Prefer JdbcTemplate.

/**
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:/spring/beans-datasource-it.xml",
	"classpath:/spring/beans-service.xml",
	"classpath:/spring/beans-web.xml" })
@Transactional
public class ContactControllerIT {
	private static final String SELECT_FIRST_NAME_QUERY =
		"select first_name from contact where id = ?";
	
	@Inject private ContactController controller;
	@Inject private SessionFactory sessionFactory;
	@Inject private DataSource dataSource;
	
	@Value("#{viewNames.contactForm}")
	private String expectedContactFormViewName;
	
	@Value("#{viewNames.updateContactSuccess}")
	private String expectedUpdateContactSuccessViewName;
	
	@Value("#{viewNames.deleteContactSuccess}")
	private String expectedDeleteContactSuccessViewName;
	
	// SimpleJdbcTemplate deprecated as of Spring 3.1; see above. Using JdbcTemplate instead.
//	private SimpleJdbcTemplate jdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	
	private MockHttpServletRequest request;
	private Model model;
	
	@Before
	public void setUp() throws Exception {
		
		// SimpleJdbcTemplate deprecated as of Spring 3.1; see above. Using JdbcTemplate instead.
//		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
		this.request = new MockHttpServletRequest();
		this.model = new ExtendedModelMap();
	}
	
	@After
	public void tearDown() throws Exception {
		this.jdbcTemplate = null;
		this.request = null;
		this.model = null;
	}
	
	@Test
	public void testGetContactHappyPath() {
		
		// Exercise code
		String viewName = controller.getContact(request, 1L, model);
		
		// Verify
		assertEquals(expectedContactFormViewName, viewName);
		
		Contact contact = (Contact) model.asMap().get("contact");
		assertNotNull(contact);
		assertEquals((Long) 1L, contact.getId());
		assertEquals("Robert", contact.getFirstName());
		assertEquals("A", contact.getMiddleInitial());
		assertEquals("Zimmerman", contact.getLastName());
		assertEquals("bobdylan@example.com", contact.getEmail());
	}
	
	@Test
	public void testUpdateContactHappyPath() {
		
		// Setup
		Contact contact = new Contact();
		contact.setFirstName("Bob");
		contact.setLastName("Dylan");
		contact.setEmail("bobdylan@example.com");
		
		BindingResult result = new BeanPropertyBindingResult(contact, "contact");
		
		// Exercise code
		String viewName = controller.updateContact(request, 1L, contact, result);
		
		// Verify
		assertEquals(expectedUpdateContactSuccessViewName, viewName);
		
		Model anotherModel = new ExtendedModelMap();
		controller.getContact(request, 1L, anotherModel);
		Contact updatedContact = (Contact) anotherModel.asMap().get("contact");
		assertEquals("Bob", updatedContact.getFirstName());
		
		// Show that we haven't flushed the update to the database yet
		String firstName = jdbcTemplate.
			queryForObject(SELECT_FIRST_NAME_QUERY, String.class, 1);
		assertEquals("Robert", firstName);
		
		// Manual flush required to avoid false positives in test
		sessionFactory.getCurrentSession().flush();
		
		// Show that the flush worked, and now the update is in the database
		String updatedFirstName = jdbcTemplate.
			queryForObject(SELECT_FIRST_NAME_QUERY, String.class, 1);
		assertEquals("Bob", updatedFirstName);
	}
	
	@Test
	public void testDeleteContactHappyPath() {
		
		// Check for existence
		controller.getContact(request, 1L, model);
		Contact contact = (Contact) model.asMap().get("contact");
		assertNotNull(contact);
		
		// Exercise code
		String viewName = controller.deleteContact(1L);
		
		// Verify
		assertEquals(expectedDeleteContactSuccessViewName, viewName);
		
		try {
			controller.getContact(request, 1L, new ExtendedModelMap());
			fail("Expected ResourceNotFoundException");
		} catch (ResourceNotFoundException e) {
			// OK, expected.
		}
		
		// Show that we haven't flushed the update to the database yet
		String firstName = jdbcTemplate.
			queryForObject(SELECT_FIRST_NAME_QUERY, String.class, 1);
		assertEquals("Robert", firstName);
		
		// Manual flush required to avoid false positives in test
		sessionFactory.getCurrentSession().flush();
		
		// Show that the flush worked, and now the row is gone
		try {
			jdbcTemplate.queryForObject(SELECT_FIRST_NAME_QUERY, String.class, 1);
			fail("Expected DataAccessException");
		} catch (DataAccessException e) {
			// OK, expected.
		}
	}
}

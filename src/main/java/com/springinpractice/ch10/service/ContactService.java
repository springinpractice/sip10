/* 
 * Copyright (c) 2013 Manning Publications Co.
 * 
 * Book: http://manning.com/wheeler/
 * Blog: http://springinpractice.com/
 * Code: https://github.com/springinpractice
 */
package com.springinpractice.ch10.service;

import java.util.List;

import com.springinpractice.ch10.model.Contact;

/**
 * <p>
 * Contact service interface.
 * </p>
 * 
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 */
public interface ContactService {

	/**
	 * <p>
	 * Creates the given contact in the persistent store.
	 * </p>
	 * 
	 * @param contact
	 *            contact to create
	 * @throws IllegalArgumentException
	 *             if <code>contact</code> is <code>null</code>
	 */
	void createContact(Contact contact);

	/**
	 * <p>
	 * Returns a list containing all contacts. Returns an empty list if there
	 * aren't any contacts.
	 * </p>
	 * 
	 * @return list of all contacts
	 */
	List<Contact> getContacts();

	/**
	 * <p>
	 * Returns the contact having the given ID, or <code>null</code> if no such
	 * contact exists.
	 * </p>
	 * 
	 * @param id
	 *            contact ID
	 * @return contact having the given ID
	 */
	Contact getContact(long id);
	
	void updateContact(Contact contact);

	/**
	 * <p>
	 * Deletes the contact having the given ID.
	 * </p>
	 * 
	 * @param id
	 *            contact ID
	 */
	void deleteContact(long id);
}

/* 
 * Copyright (c) 2013 Manning Publications Co.
 * 
 * Blog   : http://springinpractice.com/
 * GitHub : https://github.com/organizations/springinpractice
 * Book   : http://manning.com/wheeler/
 * Forum  : http://www.manning-sandbox.com/forum.jspa?forumID=503
 */
package com.springinpractice.ch10.dao;

import org.springframework.stereotype.Repository;

import com.springinpractice.ch10.model.Contact;
import com.springinpractice.dao.hibernate.AbstractHbnDao;

/**
 * <p>
 * Hibernate-backed data access object implementation for contacts.
 * </p>
 * 
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 */
@Repository
public class HbnContactDao extends AbstractHbnDao<Contact> implements ContactDao { }

/* 
 * Copyright (c) 2013 Manning Publications Co.
 * 
 * Book: http://manning.com/wheeler/
 * Blog: http://springinpractice.com/
 * Code: https://github.com/springinpractice
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

/* 
 * $Id: HbnContactDao.java 20 2010-05-02 08:36:22Z  $
 * 
 * Copyright (c) 2010 Manning Publications Co.
 * 
 * Book web site   - http://www.manning.com/wheeler/
 * Book blog       - http://springinpractice.com/
 * Author web site - http://wheelersoftware.com/
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
 * @version $Id: HbnContactDao.java 20 2010-05-02 08:36:22Z  $
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 */
@Repository
public class HbnContactDao extends AbstractHbnDao<Contact> implements ContactDao { }

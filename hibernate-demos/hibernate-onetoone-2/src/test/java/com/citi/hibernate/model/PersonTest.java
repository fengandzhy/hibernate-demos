package com.citi.hibernate.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToOne;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {

	SessionFactory sessionFactory = null;
	Session session = null;
	Transaction transaction = null;
	
	@Before
	public void init() {
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
		sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();		
	}	
	
	
	/**
	 * 由于在配置文件中one-to-one当中不能出现'column'属性，所以用配置文件
	 * 实现一对一的唯一外键关联就只有一种方法,那就是many-to-one设置成unique=true
	 * 
	 * 而用注解方式来实现一对一唯一外键关联的话，它有两种方式
	 * 一种是@ManyToOne设置unique=true
	 * 另一种就是@OneToOne 
	 * 
	 * */
	@Test
	public void doSave() throws ParseException {
		Person p1 = new Person();
		p1.setName("Amy");
		
		IDCard card = new IDCard();
		card.setNumber("123456");
		
		p1.setCard(card);
		//card.setPerson(p1);
		
		session.save(p1);
		session.save(card);
	}
	
	
	@Test
	public void doSearch() throws ParseException {
				
	}
	
	@After
	public void destory() {
		transaction.commit();//commit只会针对持久对象，判断其是否跟数据库一致,不一致发起sql语句操作数据库，本质上还是flash()方法在起作用
		session.close();
		sessionFactory.close();
	}

}

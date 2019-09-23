package org.zhouhy.hibernate.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

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
	 * 两边同时配置<one-to-one是无法完成外键关联的,就是说无法创建出一个含有外键的表,创建你了连个独立的表例如
	 * person端配置<one-to-one name="card" class="org.zhouhy.hibernate.model.IDCard"></one-to-one>
	 * idcard端配置<one-to-one name="person" class="org.zhouhy.hibernate.model.Person"></one-to-one>
	 * 
	 * 
	 * 当在many-to-one端加入 cascade="save-update"的时候,不会执行update语句
	 * 
	 * 
	 * */
	@Test
	public void doSave() throws ParseException {
		Person p1 = new Person();
		p1.setName("Amy");
		
		IDCard card = new IDCard();
		card.setNumber("123456");
		
		//p1.setCard(card);
		card.setPerson(p1);
		
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

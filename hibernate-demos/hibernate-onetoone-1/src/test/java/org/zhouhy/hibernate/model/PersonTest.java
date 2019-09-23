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
	 * ����ͬʱ����<one-to-one���޷�������������,����˵�޷�������һ����������ı�,�����������������ı�����
	 * person������<one-to-one name="card" class="org.zhouhy.hibernate.model.IDCard"></one-to-one>
	 * idcard������<one-to-one name="person" class="org.zhouhy.hibernate.model.Person"></one-to-one>
	 * 
	 * 
	 * ����many-to-one�˼��� cascade="save-update"��ʱ��,����ִ��update���
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
		transaction.commit();//commitֻ����Գ־ö����ж����Ƿ�����ݿ�һ��,��һ�·���sql���������ݿ⣬�����ϻ���flash()������������
		session.close();
		sessionFactory.close();
	}

}

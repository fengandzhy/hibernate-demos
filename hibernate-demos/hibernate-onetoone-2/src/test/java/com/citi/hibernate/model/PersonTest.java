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
	 * �����������ļ���one-to-one���в��ܳ���'column'���ԣ������������ļ�
	 * ʵ��һ��һ��Ψһ���������ֻ��һ�ַ���,�Ǿ���many-to-one���ó�unique=true
	 * 
	 * ����ע�ⷽʽ��ʵ��һ��һΨһ��������Ļ����������ַ�ʽ
	 * һ����@ManyToOne����unique=true
	 * ��һ�־���@OneToOne 
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
		transaction.commit();//commitֻ����Գ־ö����ж����Ƿ�����ݿ�һ��,��һ�·���sql���������ݿ⣬�����ϻ���flash()������������
		session.close();
		sessionFactory.close();
	}

}

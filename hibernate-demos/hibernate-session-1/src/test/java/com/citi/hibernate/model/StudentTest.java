package com.citi.hibernate.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StudentTest {

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
	 * ��ʱ״̬����
	 * 1. session������û���������
	 * 2. ���ݿ���û����Ӧ����(�������OID�����ݱ����id��һ�µ�)
	 * save�������һ����ʱ���󱣴�Ϊ�־û�����
	 * */
	//@Test
	public void testTransient() {
		//new �����Ķ���������ʱ״̬
		Student stu = new Student("sam","female",new Date());
		session.save(stu);//��commit��ʱ��Ż�������ݿ�
	}
	
	/**
	 * ����ʱ״̬���־�״̬
	 * */
	//@Test
	public void testPersistent() {
		//new �����Ķ���������ʱ״̬
		Student stu = new Student("abc","female",new Date());
		session.save(stu);//��commit��ʱ��Ż�������ݿ�
		System.out.println(stu);
		stu.setName("3");	
		//stu.setId(8);//���������Ϊ������ID�����޷����µ����ݿ����
	}
	
	/**
	 * stuֱ�Ӿ��ǳ־û�������Ϊͨ��get����ȡ
	 * */
	//@Test
	public void testPersistent1() {
		Student stu = session.get(Student.class, 1);
		stu.setName("abddd");
	}
	
	/**
	 * clear�������������֮��,stu�Ͳ����ǳ־û�������
	 * */
	@Test
	public void testPersistent2() {
		Student stu = session.load(Student.class, 1);
		stu.setName("efss");
		session.clear();
	}
	
	
	@After
	public void destory() {
		transaction.commit();//commitֻ����Գ־ö����ж����Ƿ�����ݿ�һ��,��һ�·���sql���������ݿ⣬�����ϻ���flash()������������
		session.close();
		sessionFactory.close();
	}

}

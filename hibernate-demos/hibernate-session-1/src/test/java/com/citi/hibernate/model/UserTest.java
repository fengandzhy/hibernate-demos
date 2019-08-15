package com.citi.hibernate.model;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

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
	
	//@Test
	public void testSave() {
		User user = new User("sam","111111");
		System.out.println(user);
		session.save(user);			
	}
	
	/**
	 * ���ۣ� session�л��棬������Ķ��󣬲����������ñ�����ʧ�����! 
	 * ֻҪ�������Ѿ����ڵĶ���, �ڲ�ѯ��ʱ��, Hibernate�����ȴӻ�����ȡ������, 
	 * �������ٴ�ȥ�������ݿ�, ֻ�л����в����ڵ�����, �Ż�ȥ����SQL����ѯ���ݿ�, 
	 * ���Ի��漫��ļ����˷������ݿ��Ƶ��!
	 * */
	//@Test
	public void testGet() {
		User user = session.get(User.class,1);
		user = null;
		user = session.get(User.class,1);
		System.out.println(user);
	}
	
	/**
	 *  flush()��ǿ�������ݿ�������ݼ�¼��session�л���Ķ������ݱ���һ�£�
	 *  ��һ�¾ͷ���update����sql����޸���������һ��, һ��, ��������,���ᷢȥsql���.
	 *  
	 *  �й���flush()������֪ʶ�㣺
		a���������commit()�ύ֮ǰ�Զ�������session��flush()������Ȼ�����ύ����
		b��flush()���ܻᷢ��sql���,���ǲ����ύ����
	 *  
	 *  ����һ��Ŷ�����˵����ִ�е�session.flush();��ʱ����Ϊ��ȥ����update��һ������
	 *  ��ʱuser�����ֵҲ�Ǹ����ݿ����ֵ��һ�£����������ᷢ��update���.
	 *  �ɼ�flush()�Ǹ������ȡ����ֵ���ж������ݿ����ֵ�Ƿ�һ�µ�
	 * */
	//@Test
	public void testFlush() {
		User user = session.get(User.class,1);
		user.setUsername("abcd");
		session.flush();//���﷢����һ��update��䣬����������䲢û��������,ֱ�����������ύ��ʱ��		
	}
	
	/**
	 * ��refresh(): ����ǿ�Ʒ���һ��select���, ��֤session�����е����ݺ����ݿ������ݼ�¼��һ�µ�, 
	 * ������ֲ�һ�������޸Ļ����еĶ����е���������һ�¡�
	 * */
	//@Test
	public void testReflush() {
		User user = session.get(User.class,1);
		user.setUsername("abcd");
		System.out.println(user);
		session.refresh(user);
		System.out.println(user);
	}
	
	/**
	 * ����Hibernate��������ķ�������clear() : ����session����
	 * */
	//@Test
	public void testClear() {
		User user = session.get(User.class,1);
		System.out.println(user);
		session.clear();
		user = session.get(User.class, 1);//������Ȼ�ᷢ��select���
		System.out.println(user);
	}
	
	
	
	@After
	public void destory() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}

}

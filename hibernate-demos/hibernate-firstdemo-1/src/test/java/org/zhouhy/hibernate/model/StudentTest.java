package org.zhouhy.hibernate.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

public class StudentTest {

	@Test
	public void test() {
		/*�ٴ���һ��SessionFactory������*/
		SessionFactory sessionFactory = null;
		Configuration configuration = new Configuration().configure();
		//hibernate�涨,�������û����Ҫ��Ч,�������û����ע�ᵽһ������ע����
		ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();		
		sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		/*��ͨ�������࿪��Session����*/
		Session session = sessionFactory.openSession();		
		/*�ۿ�ʼ����*/
		Transaction transaction = session.beginTransaction();
		/*��ִ�����ݿ�Ĳ���*/
		Student stu = new Student("sam","male",new Date());
		session.save(stu);
		
		/*���ύ����*/
		transaction.commit();
		/*�޹ر�Session*/
		session.close();
		/*�߹رչ�����*/
		sessionFactory.close();		
	}
}

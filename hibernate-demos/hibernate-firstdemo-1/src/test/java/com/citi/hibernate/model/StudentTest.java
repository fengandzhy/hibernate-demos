package com.citi.hibernate.model;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
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

		/*�ۿ�ʼ����*/

		/*��ִ�����ݿ�Ĳ���*/

		/*���ύ����*/

		/*�޹ر�Session*/

		/*�߹رչ�����*/

		
		
		
		/**���������ļ������Ϣ*/
		
		
		
		
	}

}

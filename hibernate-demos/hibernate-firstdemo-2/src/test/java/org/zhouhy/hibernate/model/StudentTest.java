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
		try {
			Configuration configuration = new Configuration().configure();
			ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
			SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Student stu = new Student("sam", "male", new Date());
			session.save(stu);
			transaction.commit();
			session.close();
			sessionFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}

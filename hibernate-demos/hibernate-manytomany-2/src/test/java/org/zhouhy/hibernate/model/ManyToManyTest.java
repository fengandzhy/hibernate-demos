package org.zhouhy.hibernate.model;


import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManyToManyTest {
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
	 * 1.��teacher����������ʱ
	 * 	@ManyToMany
    	@JoinTable(name="t_teachars_courses",                    
            joinColumns= {@JoinColumn(name="teacher_id")},        
            inverseJoinColumns= {@JoinColumn(name="course_id")})
	 	teacher��ά��t_teachars_courses�������
	 	
	 * */
	@Test
	public void testSave() {
		Teacher t1 = new Teacher();
		t1.setName("a");
		
		Teacher t2 = new Teacher();
		t2.setName("b");
		
		Course c1 = new Course();
		c1.setName("1");
		
		Course c2 = new Course();
		c2.setName("2");
		
//		t1.getCourses().add(c1);
//		t1.getCourses().add(c2);
//		t2.getCourses().add(c1);
//		t2.getCourses().add(c2);
		
		c1.getTeachers().add(t1);
		c1.getTeachers().add(t2);
		c2.getTeachers().add(t1);
		c2.getTeachers().add(t2);
		
		session.save(t1);
		session.save(t2);
		session.save(c1);
		session.save(c2);
	}	
	
	@After
	public void destory() {
		transaction.commit();//commitֻ����Գ־ö����ж����Ƿ�����ݿ�һ��,��һ�·���sql���������ݿ⣬�����ϻ���flash()������������
		session.close();
		sessionFactory.close();
	}
}

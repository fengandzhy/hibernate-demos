package com.citi.hibernate.model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	@Test
	public void doWorkTest() {
		session.doWork(new Work() {

			public void execute(Connection connection) throws SQLException {
				System.out.println(connection);
				
			}
			
		});
	}
	
	@Test
	public void doSave() throws ParseException {
		Teacher teacher = new Teacher();
		teacher.setName("12");
		
		Student stu1 = new Student();
		stu1.setName("45");
		stu1.setTeacher(teacher);
		
		Student stu2 = new Student();
		stu2.setName("78");
		stu2.setTeacher(teacher);
		
		
		//����: ������һ��һ�˵�����,�����sql�����, �����Ӷ��һ�˵����ݷ���sql���������, �����ȼ�һ��һ��, Ч�ʸ�Щ!
		session.save(stu1);
		session.save(stu2);
		session.save(teacher);
		
	}
	
	/**
	 * ����: Ĭ�������, ��ѯ���һ�˶���, ֻҪûʹ�õ������Ķ���, 
	 * ���ᷢ������Ķ���Ĳ�ѯ! ��Ϊʹ�õ�������, 
	 * ������ʹ�ù�������֮ǰ�ر�session, ��Ȼ�����������쳣!
	 * */
	@Test
	public void doSearch() throws ParseException {
		Student student = session.load(Student.class, 1);
		System.out.println(student);
		System.out.println(student.getTeacher().getName());		
	}
	
	@After
	public void destory() {
		transaction.commit();//commitֻ����Գ־ö����ж����Ƿ�����ݿ�һ��,��һ�·���sql���������ݿ⣬�����ϻ���flash()������������
		session.close();
		sessionFactory.close();
	}

}

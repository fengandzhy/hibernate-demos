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
		
		
		//结论: 先增加一的一端的数据,发起的sql语句少, 先增加多的一端的数据发起sql语句条数多, 建议先加一的一端, 效率高些!
		session.save(stu1);
		session.save(stu2);
		session.save(teacher);
		
	}
	
	/**
	 * 结论: 默认情况下, 查询多的一端对象, 只要没使用到关联的对象, 
	 * 不会发起关联的对象的查询! 因为使用的懒加载, 
	 * 所以在使用关联对象之前关闭session, 必然发生赖加载异常!
	 * */
	@Test
	public void doSearch() throws ParseException {
		Student student = session.load(Student.class, 1);
		System.out.println(student);
		System.out.println(student.getTeacher().getName());		
	}
	
	@After
	public void destory() {
		transaction.commit();//commit只会针对持久对象，判断其是否跟数据库一致,不一致发起sql语句操作数据库，本质上还是flash()方法在起作用
		session.close();
		sessionFactory.close();
	}

}

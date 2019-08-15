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
	 * 临时状态特征
	 * 1. session缓存中没有这个对象
	 * 2. 数据库中没有相应对象(对象里的OID与数据表里的id是一致的)
	 * save方法会把一个临时对象保存为持久化对象
	 * */
	//@Test
	public void testTransient() {
		//new 出来的对象先是临时状态
		Student stu = new Student("sam","female",new Date());
		session.save(stu);//在commit的时候才会进入数据库
	}
	
	/**
	 * 从临时状态到持久状态
	 * */
	//@Test
	public void testPersistent() {
		//new 出来的对象先是临时状态
		Student stu = new Student("abc","female",new Date());
		session.save(stu);//在commit的时候才会进入数据库
		System.out.println(stu);
		stu.setName("3");	
		//stu.setId(8);//如果这里人为设置了ID，是无法更新到数据库里的
	}
	
	/**
	 * stu直接就是持久化对象因为通过get方法取
	 * */
	//@Test
	public void testPersistent1() {
		Student stu = session.get(Student.class, 1);
		stu.setName("abddd");
	}
	
	/**
	 * clear方法将缓存清除之后,stu就不再是持久化对象了
	 * */
	@Test
	public void testPersistent2() {
		Student stu = session.load(Student.class, 1);
		stu.setName("efss");
		session.clear();
	}
	
	
	@After
	public void destory() {
		transaction.commit();//commit只会针对持久对象，判断其是否跟数据库一致,不一致发起sql语句操作数据库，本质上还是flash()方法在起作用
		session.close();
		sessionFactory.close();
	}

}

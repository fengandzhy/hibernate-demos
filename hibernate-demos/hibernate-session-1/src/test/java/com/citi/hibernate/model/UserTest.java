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
	 * 结论： session有缓存，缓存里的对象，不会随着引用变量消失而清空! 
	 * 只要缓存中已经存在的对象, 在查询的时候, Hibernate会优先从缓存中取出数据, 
	 * 而不会再次去访问数据库, 只有缓存中不存在的数据, 才会去发起SQL语句查询数据库, 
	 * 所以缓存极大的减少了访问数据库的频率!
	 * */
	//@Test
	public void testGet() {
		User user = session.get(User.class,1);
		user = null;
		user = session.get(User.class,1);
		System.out.println(user);
	}
	
	/**
	 *  flush()：强制让数据库里的数据记录和session中缓存的对象数据保存一致，
	 *  不一致就发起update这条sql语句修改数据让其一致, 一致, 它不动作,不会发去sql语句.
	 *  
	 *  有关于flush()方法的知识点：
		a、在事务的commit()提交之前自动调用了session的flush()方法，然后再提交事务。
		b、flush()可能会发送sql语句,但是不会提交事务。
	 *  
	 *  补充一点哦，如果说程序执行到session.flush();的时候人为的去数据update了一条数据
	 *  此时user里面的值也是跟数据库里的值不一致，但是它不会发起update语句.
	 *  可见flush()是根据最初取到的值来判断与数据库里的值是否一致的
	 * */
	//@Test
	public void testFlush() {
		User user = session.get(User.class,1);
		user.setUsername("abcd");
		session.flush();//这里发起了一条update语句，但是这条语句并没有起作用,直到发起事物提交的时候		
	}
	
	/**
	 * ②refresh(): 它会强制发出一条select语句, 保证session缓存中的数据和数据库里数据记录是一致的, 
	 * 如果发现不一致它会修改缓存中的对象中的数据让其一致。
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
	 * 另外Hibernate操作缓存的方法还有clear() : 清理session缓存
	 * */
	//@Test
	public void testClear() {
		User user = session.get(User.class,1);
		System.out.println(user);
		session.clear();
		user = session.get(User.class, 1);//这里仍然会发起select语句
		System.out.println(user);
	}
	
	
	
	@After
	public void destory() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}

}

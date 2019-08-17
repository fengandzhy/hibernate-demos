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
	   *    持久化对象如果commit的时候，发现其跟数据库不一致，则发起update语句
	 * 
	 * */
	//@Test
	public void testPersistent2() {
		Student stu = session.load(Student.class, 1);
		stu.setName("2");//这里set的如果跟数据库内容一致，将不发起update语句,如果不一致发起update语句
		session.clear();
		//session.update(stu);
	}
	
	/**
	 *  游离对象测试
	 * 	update 方法在后台打印出来是一个update语句, 但如果update里面的对象之前就是持久状态的话，不执行update
	 * 	它是先将处理的对象放置到session缓存中去
	 * 	另外hibernate不允许修改处于持久状态的对象的id 
	 * */
	//@Test
	public void testDetached() {
		Student stu = new Student("abc","female",new Date());
		stu.setId(1); //虽然stu是new出来的，但是这里如果设置了id，就成了游离对象
		stu.setName("2");
		session.update(stu);//update方法使用的前提就是对象的id必须是在数据库里有值
	}
	
	/**
	 * 执行了delete之后对象变成了一个临时对象
	 * 之后它会被垃圾回收删除
	 * */
	//@Test
	public void testDetached1() {
		Student stu = new Student("abc","female",new Date());
		stu.setId(7); 		
		session.delete(stu);//delete方法，第一将对象从session缓存中删除,第二将数据库相应记录删除
	}
	
	/**
	 * 如果id设置的数据库里不存在,那么同样执行update语句,只是这个执行效果没有
	 * 因为没有对应的记录
	 * 凡是设置ID的，不管数据库里有没有对应的值，都会去执行update方法,感觉凡是
	 * 设置ID的都是游离对象
	 * */
	//@Test
	public void testDetached2() {
		Student stu = new Student("abeec","female",new Date());
		stu.setId(6); 		
		session.saveOrUpdate(stu);//如果是游离状态则调用update方法,如果是临时状态则调用save方法
	}
	
	
	/**
	 * 下面代码报错
	 * session的缓存中不许用出现两个或两个以上有相同ID值的对象
	 * */
	//@Test
	public void testDetached3() {
		Student stu = session.load(Student.class, 1);		
		Student stu1 = new Student();
		stu1.setId(1);				
		session.update(stu1);
	}
	
	/**
	 *  save方法
		这个方法表示将一个对象保存到数据库中，
		可以将一个不含OID的new出来的临时对象转换为
		一个处于Session缓存中具有OID的持久化对象。
		
		需要注意的是：在save方法前设置OID是无效的但是也不会报错，
		在save方法之后设置OID程序会抛出异常，
		因为持久化之后的对象的OID是不可更改的，
		因为对象的OID此时和数据库中的一条记录对应。
		
		结论总结:
	1.临时对象变持久对象
	2.给对象分配id,这个id叫oid, 它和数据库的记录id对应一致
	3.执行save方法时会发起一条insert语句, 但要等到事务提交时才会作用到数据库
	4.save方法前设置id无效, save方法后设置id报异常,持久对象的id不准修改
	 * */
	//@Test
	public void testSave() {
		Student stu = new Student("abeec","female",new Date());		
		System.out.println(stu);				
		session.save(stu);
		System.out.println(stu);	
	}
	
	//@Test
	public void testPersist() {
		Student stu = new Student("abeec","female",new Date());
		stu.setId(20);//在persist之前设置id报错这也是跟save方法唯一的区别
		session.persist(stu);
		System.out.println(stu);	
	}
	
	
	/**
	 * 1.get方法会立即加载对象，发起sql语句
	 * 2.load方法不会立即加载对象，而是当加载的对象被使用的时候才会去加载对象，发起sql语句
	 * 3.get方法返回的是就是对象本身
	 * 4.load方法返回值不是student的对象本身而是student对象的一个代理
	 * 5.get方法，如果查询的数据在数据库中没有对应的id的记录值, get方法返回null, 不报异常, 
	 * 6.load方法，当使用加载对象的时候, 代理对象才加载真正的对象并发起sql,这时才发现查不到对象,所以就只能报出异常了!
	 * 
	 * 
	 * */
	@Test
	public void testGet() {
		Student stu = session.get(Student.class, 1);
		System.out.println(stu.getClass());
		//System.out.println(stu);	
	}
	
	@Test
	public void testLoad() {
		Student stu = session.load(Student.class, 1);
		System.out.println(stu.getClass());
		//System.out.println(stu);
	}
	@After
	public void destory() {
		transaction.commit();//commit只会针对持久对象，判断其是否跟数据库一致,不一致发起sql语句操作数据库，本质上还是flash()方法在起作用
		session.close();
		sessionFactory.close();
	}

}

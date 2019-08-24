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
	@Test
	public void testTransient() {
		//new 出来的对象先是临时状态
		Student stu = new Student("sam","female",new Date());
		session.save(stu);//在commit的时候才会进入数据库
//		stu = session.get(Student.class, 15);
//		stu.setName("123456");
		
	}
	
	/**
	 * 从临时状态到持久状态
	 * */
	@Test
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
	 * 
	 * */
	@Test
	public void testPersistent1() {
		Student stu = session.get(Student.class, 1);
		stu.setName("abddd");
	}
	
	/**
	 * clear方法将缓存清除之后,stu就不再是持久化对象了
	   *    持久化对象如果commit的时候，发现其跟数据库不一致，则发起update语句
	 * 
	 * */
	@Test
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
	@Test
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
	5.对于一个持久状态的对象使用session.save()不会发起insert语句
	 * */
	@Test
	public void testSave() {
		Student stu = new Student("abeec","female",new Date());		
		System.out.println(stu);				
		session.save(stu);
		System.out.println(stu);	
	}
	
	@Test
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
		//session.save(stu);
		//System.out.println(stu);	
	}
	
	@Test
	public void testLoad() {
		Student stu = session.load(Student.class, 1);
		System.out.println(stu.getClass());
		//System.out.println(stu);
	}
	
	/**
	 * update方法
	 * 1.这个方法顾名思义就是更新一个对象在数据库中的对照情况，从而使一个游离对象转换为一个持久化对象
	 * 2.若是更新一个持久化对象，不需要再显式子的进行update方法，因为在commit方法中已经进行过flush了,它会自动发起update语句
	 * 3.若是关闭了一个session，而又打开了一个session，这时，
	 * 前一个session对象相对于第二个session来说就是游离的对象了，
	 * 此时，做更新的时候, 必须显式的用第二个session进行update一下
	 * 才可以将这个对象变成相对于第二个session的持久化对象。才会发起sql语句
	 * 4.可以在hbm.xml文件的class节点设置一个属性叫做select-before-update为true，就可以避免当对象和数据库里的值完全一致时仍然会发起update语句
	 * 
	 * 自我总结
	 * 1. session.update(stu);如果stu之前是游离对象或者是临时对象时,均会发起update语句
	 * 2. 如果是临时对象，它没有ID，即便发起update语句也无法影响到数据库
	 * 3. 如果是游离对象，此时它的ID值在数据库里没有相应记录,也无法影响到数据库
	 * 
	 * */
	@Test
	public void testUpdate() {
		Student stu = new Student("abc","female",new Date());
		stu.setId(7);
		session.update(stu);
		
	}
	
	/**
	 * session.delete(stu);
	 * 1.如果此时stu是持久对象,则先将stu从session缓存中清除,提交时再执行delete语句将其从数据库清除
	 * 2.如果此时stu是游离对象的话，如果id值在数据库里有值，那么提交时执行delete语句
	 * 3.如果stu的id在数据库里不存在,也会发起delete语句,只不过没有作用
	 * 4.delete将stu从session缓存中移除后是无法通过session.update(stu)重新加入缓存的 ,删除了就无法update了
	 * 5.delete将stu从session缓存中移除后是可以通过session.save(stu)重新加入缓存的
	 * 
	 * */
	@Test
	public void testDelete() {
		Student stu = session.get(Student.class, 8);
		//Student stu = new Student("abc","female",new Date());		
		
		System.out.println(stu);
		session.delete(stu);
//		System.out.println(stu);
//		session.save(stu);
//		stu.setName("def");
		
	}
	
	
	/**
	 *  evict方法就是将持久化对象从session缓存中删除，使其成为一个游离的对象
	 * 1.先执行一个select语句因为get方法
	 * 2.再执行一个insert语句,因为save方法，之前stu是游离状态的对象
	 * 3.最后执行一条update语句,因为save方法之后,stu变成了持久状态,值改变则commit的时候自动发起update语句
	 * */
	@Test
	public void testEvict() {
		Student stu = session.get(Student.class, 15);		
		session.evict(stu);
		session.save(stu);
		stu.setName("add");
	}
	
	@After
	public void destory() {
		transaction.commit();//commit只会针对持久对象，判断其是否跟数据库一致,不一致发起sql语句操作数据库，本质上还是flash()方法在起作用
		session.close();
		sessionFactory.close();
	}

}

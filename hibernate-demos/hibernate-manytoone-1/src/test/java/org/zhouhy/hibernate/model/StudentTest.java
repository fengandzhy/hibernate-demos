package org.zhouhy.hibernate.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	/**
	 * 当在teacher的set节点上设置inverse="true"的时候，表名teacher不再维护外键
	 * 也就是说，teacher.setStudents(students); 不再发起update语句去改变student1表的外键值
	 * 默认情况下，两端都要维护外键，也就是说，stu1.setTeacher(teacher);和
	 * teacher.setStudents(students);都要去改这个外键的值,很显然这是多余的
	 * 另外非常要注意的是inverse这个属性不能出现在many-to-one 这个节点上
	 * 
	 * 
	 * 
	 * 当在teacher的set节点上设置cascade="save-update"的时候,表明保存或更改
	 * teacher的时候，会影响到相应的student对象，例如会直接吧临时状态的student改成持久状态
	 * 
	 * */
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
		
		Set<Student> students = new HashSet<>();
		students.add(stu1);
		students.add(stu2);
		teacher.setStudents(students);
		
		//结论: 先增加一的一端的数据,发起的sql语句少, 先增加多的一端的数据发起sql语句条数多, 建议先加一的一端, 效率高些!
//		session.save(stu1);
//		session.save(stu2);
		session.save(teacher);
		
	}
	
	/**
	 * 结论: 默认情况下, 查询多的一端对象, 只要没使用到关联的对象, 
	 * 不会发起关联的对象的查询! 因为使用的懒加载, 
	 * 所以在使用关联对象之前关闭session,也就是说在 student.getTeacher().getName()之前关闭session 
	 * 必然发生赖加载异常!
	 * 当fetch="join"只在student端的时候，查询student会把teacher也join出来,反之查询teacher不会把student join出来
	 * */
	@Test
	public void doSearch() throws ParseException {
		Student student = session.load(Student.class, 1);
		System.out.println(student);
		//session.close();
		System.out.println(student.getTeacher().getName());		
	}
	
	
	/**
	 * 这里默认也是lazy加载，也即是说只有当用到student的时候，才会去数据库里查询
	 * */
	@Test
	public void doSearch1() throws ParseException {
		Teacher teacher = session.load(Teacher.class, 1);
		System.out.println(teacher);
		session.close();
		System.out.println(teacher.getStudents());		
	}
	
	/**
	 * 当teacher配置lazy="false"时，程序走到System.out.println(teacher);
	 * 就会发起两条sql语句，一条去查teacher 一条去查student
	 * */
	@Test
	public void doSearch2() throws ParseException {
		Teacher teacher = session.load(Teacher.class, 1);
		System.out.println(teacher);
		System.out.println(teacher.getStudents());		
	}
	
	/**
	 * 当student配置lazy="false"时，程序走到System.out.println(student);
	 * 就会发起两条sql语句，一条去查teacher 一条去查student
	 * */
	@Test
	public void doSearch3() throws ParseException {
		Student student = session.load(Student.class, 1);
		System.out.println(student);
		System.out.println(student.getTeacher().getName());		
	}
	
	/**
	 * 两边同时lazy="false"时，程序走到System.out.println(student);
	 * 就会发起2+1条sql语句，
	 * 注意在many-to-one端不能写lazy="true" 在这端默认为true
	 * */
	@Test
	public void doSearch4() throws ParseException {
		Student student = session.load(Student.class, 1);
		System.out.println(student);
		System.out.println(student.getTeacher().getName());		
	}
	
	
	/**
	 * 当teacher端写成fetch="join"时，System.out.println(teacher);它会运行一条带join的sql语句
	 * 这里也能验证hibernate先从缓存中取值后从数据库中取值的，首先当运行到System.out.println(teacher);时
	 * 发起一条left join的sql语句，将teacher和student都加载到缓存中
	 * 所以teacher.getStudents()时，也是先去缓存中去student对象的，如果此时在执行teacher.getStudents()某条student前发生了改变
	 * 它不会反应出来。
	 * 
	 * */
	@Test
	public void doSearch5() throws ParseException {
		Teacher teacher = session.load(Teacher.class, 1);
		System.out.println(teacher);
		System.out.println(teacher.getStudents());		
	}
	
	/**
	 * fetch="join"似乎只对load和get的查询起作用,就是说像下面这种用hql语句查询的，尽管teacher端配置了fetch="join" 
	 * 但是不会被left join 出来
	 * */
	@Test
	public void doSearch6() throws ParseException {
		String hql = "from Teacher where id =1";
		List<Teacher> list = session.createQuery(hql).list();
		Teacher teacher = list.get(0);
		System.out.println(teacher);
		//session.close();
		System.out.println(teacher.getStudents());		
	}
	
	
	@After
	public void destory() {
		transaction.commit();//commit只会针对持久对象，判断其是否跟数据库一致,不一致发起sql语句操作数据库，本质上还是flash()方法在起作用
		session.close();
		sessionFactory.close();
	}

}

package org.zhouhy.hibernate.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;
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
		session.save(stu1);
		session.save(stu2);
		session.save(teacher);
		
	}
	
	
	/**
	 * 	当teacher端的配置为
	 * @OneToMany
	 * @JoinColumn(name="teacher_id")
	 * 	1.如果先保存student再保存teacher的时候会执行4条update语句,	因为当你插入student的时候
	 * 	还要插入对应的teacher_id,而此时此时数据库中并不存在这个Id,所以，Hibernate会先为我们插入一个空值
	 * 	然后插入Teacher之后再根据此时生成的teacher_id去更新学生的信息
	 *  
	 *  2.如果先保存了teacher再保存student那么就只会执行2条update语句,
	 *  	
	 * */
	@Test
	public void testSave1() {
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
		
		session.save(teacher);
		session.save(stu1);
		session.save(stu2);		
	}
	
	/**
	 * 当teacher端的配置为 @OneToMany(mappedBy="teacher")的时候
	 * 1.先保存student再保存teacher时,生成三条insert语句两条update语句
	 * 2.先保存teacher再保存student时,生成三条insert语句,没有update语句
	 * 也就是说mappedBy也就是相当于inverse=true,teacher端不再参与外键维护
	 * 一般情况下外键在多的一方,维护外键就交于多的一方,如果让1端维护,始终都会多出update语句
	 * 
	 * 
	 * */
	@Test
	public void testSave2() {
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
		
		session.save(teacher);
		session.save(stu1);
		session.save(stu2);			
	}
	
	/**
	 * 当student端配置为@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	 * 表明保存或者更改student对象的时候，会影响到teacher对象
	 * 
	 * teacher当引入javax.persistence.CascadeType时是可以这么配置的@OneToMany(mappedBy="teacher",cascade = CascadeType.DETACH)
	 * 如果引入org.hibernate.annotations.CascadeType就无法像上述那样配置,应该是@Cascade(value = CascadeType.DETACH)
	 * 反过来讲当teacher端配置成@Cascade(value = CascadeType.SAVE_UPDATE),一样保存teacher的时候会影响到student
	 * 
	 * */
	@Test
	public void testSave3() {
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
		
		session.save(teacher);
//		session.save(stu1);
//		session.save(stu2);			
	}
	
	/**
	 * 当teacher端配置@OneToMany(mappedBy="teacher",fetch = FetchType.EAGER)
	 * 查询teacher的时候会把对应的student对象也给查出来，反之fetch = FetchType.LAZY的时候,
	 * 只有当执行Set<Student> stu = teacher.getStudents(),并使用这个stu对象时才会执行查询student操作
	 * 
	 * 	@OneToMany(mappedBy="teacher", fetch=FetchType.LAZY)
	 * 	@Fetch(FetchMode.SELECT)
	 * 上述配置不产生join连接	
	 * 
	 * @OneToMany(mappedBy="teacher", fetch=FetchType.LAZY)
	 * @Fetch(FetchMode.JOIN)
	 * 上述fetch=FetchType.LAZY失效因为FetchMode.JOIN会直接join把student找到	
	 * 
	 * 
	 * @OneToMany(mappedBy="teacher", fetch=FetchType.EAGER)
	 * @Fetch(FetchMode.SELECT)
	 * 直接执行N+1条sql语句
	 * 
	 * 	
	 * */
	@Test
	public void doSearch() throws ParseException {
		Teacher teacher = session.get(Teacher.class,1);
		Set<Student> stu =  teacher.getStudents();
		System.out.println(stu);
	}
	
	/**
	 * 当student端配置@ManyToOne(fetch = FetchType.LAZY)效果跟teacher端一样
	 * student是多端默认的是eager加载，就是不配置FetchType它也会用left join 方式加载teacher
	 * 
	 * 如果student端配置@Fetch(FetchMode.SELECT)不配置FetchType这里会执行n+1条sql语句
	 * 由此可见FetchType默认是eager加载
	 * */
	@Test
	public void doSearch1() throws ParseException {
		System.out.println();
		Student stu1 = session.get(Student.class,8);
//		Teacher teacher = stu1.getTeacher();
//		System.out.println(teacher);
	}
	
	/**
	     *  以下这段代码足以证明一个逻辑，hibernate先会从缓存中取数据,然后再从数据库中取
	     * 用debug的方式来运行这段代码, 运行到这里的时候List list=query.list();	 会输出一条sql语句(inner join)，会将teacher和student都加载到session中
	     * 但是 Set<Student> s = t.getStudents();运行到这里的时候，它不会去输出一条查询student的sql语句，因为这个lazy加载,
	     * 只有当Student stu:s，它才会去输出查询student的sql语句,但是它不会去查表，而是在缓存中查到相应的student对象，最有利的证据就是，
	     * 用debug运行这段代码的时候，当运行到Teacher t = (Teacher) object[0];停住，把相应的student的值从数据库里改掉，但是发现这里输出的还是修改之前的
	     * 可见它不是从数据库中取的而是从缓存中取的
	     * 虽然输出了sql语句但是不会去查库
	 * */
	
	@Test
	public void doSearch2() throws ParseException {
		String hql = "from Teacher t inner join t.students s where t.id=:id";
		
		Query query=session.createQuery(hql);
		query.setParameter("id", 1);
		
		List list=query.list();
		Object[] object = (Object[])list.get(0);
		Teacher t = (Teacher) object[0];
		Set<Student> s = t.getStudents();
		for(Student stu:s) {
			System.out.println(stu);
		}
		System.out.println("aaaaaaaa");
		
	}
	
	@After
	public void destory() {
		transaction.commit();//commit只会针对持久对象，判断其是否跟数据库一致,不一致发起sql语句操作数据库，本质上还是flash()方法在起作用
		session.close();
		sessionFactory.close();
	}

}

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
	 * ����teacher��set�ڵ�������inverse="true"��ʱ�򣬱���teacher����ά�����
	 * Ҳ����˵��teacher.setStudents(students); ���ٷ���update���ȥ�ı�student1������ֵ
	 * Ĭ������£����˶�Ҫά�������Ҳ����˵��stu1.setTeacher(teacher);��
	 * teacher.setStudents(students);��Ҫȥ����������ֵ,����Ȼ���Ƕ����
	 * ����ǳ�Ҫע�����inverse������Բ��ܳ�����many-to-one ����ڵ���
	 * 
	 * 
	 * 
	 * ����teacher��set�ڵ�������cascade="save-update"��ʱ��,������������
	 * teacher��ʱ�򣬻�Ӱ�쵽��Ӧ��student���������ֱ�Ӱ���ʱ״̬��student�ĳɳ־�״̬
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
		
		//����: ������һ��һ�˵�����,�����sql�����, �����Ӷ��һ�˵����ݷ���sql���������, �����ȼ�һ��һ��, Ч�ʸ�Щ!
		session.save(stu1);
		session.save(stu2);
		session.save(teacher);
		
	}
	
	
	/**
	 * 	��teacher�˵�����Ϊ
	 * @OneToMany
	 * @JoinColumn(name="teacher_id")
	 * 	1.����ȱ���student�ٱ���teacher��ʱ���ִ��4��update���,	��Ϊ�������student��ʱ��
	 * 	��Ҫ�����Ӧ��teacher_id,����ʱ��ʱ���ݿ��в����������Id,���ԣ�Hibernate����Ϊ���ǲ���һ����ֵ
	 * 	Ȼ�����Teacher֮���ٸ��ݴ�ʱ���ɵ�teacher_idȥ����ѧ������Ϣ
	 *  
	 *  2.����ȱ�����teacher�ٱ���student��ô��ֻ��ִ��2��update���,
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
	 * ��teacher�˵�����Ϊ @OneToMany(mappedBy="teacher")��ʱ��
	 * 1.�ȱ���student�ٱ���teacherʱ,��������insert�������update���
	 * 2.�ȱ���teacher�ٱ���studentʱ,��������insert���,û��update���
	 * Ҳ����˵mappedByҲ�����൱��inverse=true,teacher�˲��ٲ������ά��
	 * һ�����������ڶ��һ��,ά������ͽ��ڶ��һ��,�����1��ά��,ʼ�ն�����update���
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
	 * ��student������Ϊ@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	 * ����������߸���student�����ʱ�򣬻�Ӱ�쵽teacher����
	 * 
	 * teacher������javax.persistence.CascadeTypeʱ�ǿ�����ô���õ�@OneToMany(mappedBy="teacher",cascade = CascadeType.DETACH)
	 * �������org.hibernate.annotations.CascadeType���޷���������������,Ӧ����@Cascade(value = CascadeType.DETACH)
	 * ����������teacher�����ó�@Cascade(value = CascadeType.SAVE_UPDATE),һ������teacher��ʱ���Ӱ�쵽student
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
	 * ��teacher������@OneToMany(mappedBy="teacher",fetch = FetchType.EAGER)
	 * ��ѯteacher��ʱ���Ѷ�Ӧ��student����Ҳ�����������֮fetch = FetchType.LAZY��ʱ��,
	 * ֻ�е�ִ��Set<Student> stu = teacher.getStudents(),��ʹ�����stu����ʱ�Ż�ִ�в�ѯstudent����
	 * 
	 * 	@OneToMany(mappedBy="teacher", fetch=FetchType.LAZY)
	 * 	@Fetch(FetchMode.SELECT)
	 * �������ò�����join����	
	 * 
	 * @OneToMany(mappedBy="teacher", fetch=FetchType.LAZY)
	 * @Fetch(FetchMode.JOIN)
	 * ����fetch=FetchType.LAZYʧЧ��ΪFetchMode.JOIN��ֱ��join��student�ҵ�	
	 * 
	 * 
	 * @OneToMany(mappedBy="teacher", fetch=FetchType.EAGER)
	 * @Fetch(FetchMode.SELECT)
	 * ֱ��ִ��N+1��sql���
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
	 * ��student������@ManyToOne(fetch = FetchType.LAZY)Ч����teacher��һ��
	 * student�Ƕ��Ĭ�ϵ���eager���أ����ǲ�����FetchType��Ҳ����left join ��ʽ����teacher
	 * 
	 * ���student������@Fetch(FetchMode.SELECT)������FetchType�����ִ��n+1��sql���
	 * �ɴ˿ɼ�FetchTypeĬ����eager����
	 * */
	@Test
	public void doSearch1() throws ParseException {
		System.out.println();
		Student stu1 = session.get(Student.class,8);
//		Teacher teacher = stu1.getTeacher();
//		System.out.println(teacher);
	}
	
	/**
	     *  ������δ�������֤��һ���߼���hibernate�Ȼ�ӻ�����ȡ����,Ȼ���ٴ����ݿ���ȡ
	     * ��debug�ķ�ʽ��������δ���, ���е������ʱ��List list=query.list();	 �����һ��sql���(inner join)���Ὣteacher��student�����ص�session��
	     * ���� Set<Student> s = t.getStudents();���е������ʱ��������ȥ���һ����ѯstudent��sql��䣬��Ϊ���lazy����,
	     * ֻ�е�Student stu:s�����Ż�ȥ�����ѯstudent��sql���,����������ȥ��������ڻ����в鵽��Ӧ��student������������֤�ݾ��ǣ�
	     * ��debug������δ����ʱ�򣬵����е�Teacher t = (Teacher) object[0];ͣס������Ӧ��student��ֵ�����ݿ���ĵ������Ƿ�����������Ļ����޸�֮ǰ��
	     * �ɼ������Ǵ����ݿ���ȡ�Ķ��Ǵӻ�����ȡ��
	     * ��Ȼ�����sql��䵫�ǲ���ȥ���
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
		transaction.commit();//commitֻ����Գ־ö����ж����Ƿ�����ݿ�һ��,��һ�·���sql���������ݿ⣬�����ϻ���flash()������������
		session.close();
		sessionFactory.close();
	}

}

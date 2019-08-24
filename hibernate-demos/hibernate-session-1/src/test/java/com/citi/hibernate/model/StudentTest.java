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
	 * ��ʱ״̬����
	 * 1. session������û���������
	 * 2. ���ݿ���û����Ӧ����(�������OID�����ݱ����id��һ�µ�)
	 * save�������һ����ʱ���󱣴�Ϊ�־û�����
	 * */
	@Test
	public void testTransient() {
		//new �����Ķ���������ʱ״̬
		Student stu = new Student("sam","female",new Date());
		session.save(stu);//��commit��ʱ��Ż�������ݿ�
//		stu = session.get(Student.class, 15);
//		stu.setName("123456");
		
	}
	
	/**
	 * ����ʱ״̬���־�״̬
	 * */
	@Test
	public void testPersistent() {
		//new �����Ķ���������ʱ״̬
		Student stu = new Student("abc","female",new Date());
		session.save(stu);//��commit��ʱ��Ż�������ݿ�
		System.out.println(stu);
		stu.setName("3");	
		//stu.setId(8);//���������Ϊ������ID�����޷����µ����ݿ����
	}
	
	/**
	 * stuֱ�Ӿ��ǳ־û�������Ϊͨ��get����ȡ
	 * 
	 * */
	@Test
	public void testPersistent1() {
		Student stu = session.get(Student.class, 1);
		stu.setName("abddd");
	}
	
	/**
	 * clear�������������֮��,stu�Ͳ����ǳ־û�������
	   *    �־û��������commit��ʱ�򣬷���������ݿⲻһ�£�����update���
	 * 
	 * */
	@Test
	public void testPersistent2() {
		Student stu = session.load(Student.class, 1);
		stu.setName("2");//����set����������ݿ�����һ�£���������update���,�����һ�·���update���
		session.clear();
		//session.update(stu);
	}
	
	/**
	 *  ����������
	 * 	update �����ں�̨��ӡ������һ��update���, �����update����Ķ���֮ǰ���ǳ־�״̬�Ļ�����ִ��update
	 * 	�����Ƚ�����Ķ�����õ�session������ȥ
	 * 	����hibernate�������޸Ĵ��ڳ־�״̬�Ķ����id 
	 * */
	@Test
	public void testDetached() {
		Student stu = new Student("abc","female",new Date());
		stu.setId(1); //��Ȼstu��new�����ģ������������������id���ͳ����������
		stu.setName("2");
		session.update(stu);//update����ʹ�õ�ǰ����Ƕ����id�����������ݿ�����ֵ
	}
	
	/**
	 * ִ����delete֮���������һ����ʱ����
	 * ֮�����ᱻ��������ɾ��
	 * */
	//@Test
	public void testDetached1() {
		Student stu = new Student("abc","female",new Date());
		stu.setId(7); 		
		session.delete(stu);//delete��������һ�������session������ɾ��,�ڶ������ݿ���Ӧ��¼ɾ��
	}
	
	/**
	 * ���id���õ����ݿ��ﲻ����,��ôͬ��ִ��update���,ֻ�����ִ��Ч��û��
	 * ��Ϊû�ж�Ӧ�ļ�¼
	 * ��������ID�ģ��������ݿ�����û�ж�Ӧ��ֵ������ȥִ��update����,�о�����
	 * ����ID�Ķ����������
	 * */
	//@Test
	public void testDetached2() {
		Student stu = new Student("abeec","female",new Date());
		stu.setId(6); 		
		session.saveOrUpdate(stu);//���������״̬�����update����,�������ʱ״̬�����save����
	}
	
	
	/**
	 * ������뱨��
	 * session�Ļ����в����ó���������������������ͬIDֵ�Ķ���
	 * */
	//@Test
	public void testDetached3() {
		Student stu = session.load(Student.class, 1);		
		Student stu1 = new Student();
		stu1.setId(1);				
		session.update(stu1);
	}
	
	/**
	 *  save����
		���������ʾ��һ�����󱣴浽���ݿ��У�
		���Խ�һ������OID��new��������ʱ����ת��Ϊ
		һ������Session�����о���OID�ĳ־û�����
		
		��Ҫע����ǣ���save����ǰ����OID����Ч�ĵ���Ҳ���ᱨ��
		��save����֮������OID������׳��쳣��
		��Ϊ�־û�֮��Ķ����OID�ǲ��ɸ��ĵģ�
		��Ϊ�����OID��ʱ�����ݿ��е�һ����¼��Ӧ��
		
		�����ܽ�:
	1.��ʱ�����־ö���
	2.���������id,���id��oid, �������ݿ�ļ�¼id��Ӧһ��
	3.ִ��save����ʱ�ᷢ��һ��insert���, ��Ҫ�ȵ������ύʱ�Ż����õ����ݿ�
	4.save����ǰ����id��Ч, save����������id���쳣,�־ö����id��׼�޸�
	5.����һ���־�״̬�Ķ���ʹ��session.save()���ᷢ��insert���
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
		stu.setId(20);//��persist֮ǰ����id������Ҳ�Ǹ�save����Ψһ������
		session.persist(stu);
		System.out.println(stu);	
	}
	
	
	/**
	 * 1.get�������������ض��󣬷���sql���
	 * 2.load���������������ض��󣬶��ǵ����صĶ���ʹ�õ�ʱ��Ż�ȥ���ض��󣬷���sql���
	 * 3.get�������ص��Ǿ��Ƕ�����
	 * 4.load��������ֵ����student�Ķ��������student�����һ������
	 * 5.get�����������ѯ�����������ݿ���û�ж�Ӧ��id�ļ�¼ֵ, get��������null, �����쳣, 
	 * 6.load��������ʹ�ü��ض����ʱ��, �������ż��������Ķ��󲢷���sql,��ʱ�ŷ��ֲ鲻������,���Ծ�ֻ�ܱ����쳣��!
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
	 * update����
	 * 1.�����������˼����Ǹ���һ�����������ݿ��еĶ���������Ӷ�ʹһ���������ת��Ϊһ���־û�����
	 * 2.���Ǹ���һ���־û����󣬲���Ҫ����ʽ�ӵĽ���update��������Ϊ��commit�������Ѿ����й�flush��,�����Զ�����update���
	 * 3.���ǹر���һ��session�����ִ���һ��session����ʱ��
	 * ǰһ��session��������ڵڶ���session��˵��������Ķ����ˣ�
	 * ��ʱ�������µ�ʱ��, ������ʽ���õڶ���session����updateһ��
	 * �ſ��Խ��������������ڵڶ���session�ĳ־û����󡣲Żᷢ��sql���
	 * 4.������hbm.xml�ļ���class�ڵ�����һ�����Խ���select-before-updateΪtrue���Ϳ��Ա��⵱��������ݿ����ֵ��ȫһ��ʱ��Ȼ�ᷢ��update���
	 * 
	 * �����ܽ�
	 * 1. session.update(stu);���stu֮ǰ����������������ʱ����ʱ,���ᷢ��update���
	 * 2. �������ʱ������û��ID�����㷢��update���Ҳ�޷�Ӱ�쵽���ݿ�
	 * 3. �����������󣬴�ʱ����IDֵ�����ݿ���û����Ӧ��¼,Ҳ�޷�Ӱ�쵽���ݿ�
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
	 * 1.�����ʱstu�ǳ־ö���,���Ƚ�stu��session���������,�ύʱ��ִ��delete��佫������ݿ����
	 * 2.�����ʱstu���������Ļ������idֵ�����ݿ�����ֵ����ô�ύʱִ��delete���
	 * 3.���stu��id�����ݿ��ﲻ����,Ҳ�ᷢ��delete���,ֻ����û������
	 * 4.delete��stu��session�������Ƴ������޷�ͨ��session.update(stu)���¼��뻺��� ,ɾ���˾��޷�update��
	 * 5.delete��stu��session�������Ƴ����ǿ���ͨ��session.save(stu)���¼��뻺���
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
	 *  evict�������ǽ��־û������session������ɾ����ʹ���Ϊһ������Ķ���
	 * 1.��ִ��һ��select�����Ϊget����
	 * 2.��ִ��һ��insert���,��Ϊsave������֮ǰstu������״̬�Ķ���
	 * 3.���ִ��һ��update���,��Ϊsave����֮��,stu����˳־�״̬,ֵ�ı���commit��ʱ���Զ�����update���
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
		transaction.commit();//commitֻ����Գ־ö����ж����Ƿ�����ݿ�һ��,��һ�·���sql���������ݿ⣬�����ϻ���flash()������������
		session.close();
		sessionFactory.close();
	}

}

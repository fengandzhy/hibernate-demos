package com.citi.hibernate.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {
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
	public void ComponentSave() {
		Person rmd = new Person();
		Name name = new Name();
		name.setFirst("first1");
		name.setLast("last2");
		Map<String ,Integer> power = new HashMap<String,Integer>();
		power.put("key", 12);
		name.setPower(power);
		rmd.setName(name);
		rmd.setAge(26);
		Name2 n1 = new Name2("n1");
		Name2 n2 = new Name2("n2");
		List<Name2> list = new ArrayList<Name2>();
		list.add(n1);
		list.add(n2);
		rmd.setNicks(list);
		
		Map<Name2 ,Integer> nickPower = new HashMap<Name2,Integer>();
		nickPower.put(n1, 100);
		rmd.setNickPower(nickPower);

		session.save(rmd);

	}
	
	@After
	public void destory() {
		transaction.commit();//commit只会针对持久对象，判断其是否跟数据库一致,不一致发起sql语句操作数据库，本质上还是flash()方法在起作用
		session.close();
		sessionFactory.close();
	}
}

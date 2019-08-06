package com.citi.hibernate.model;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

public class StudentTest {

	@Test
	public void test() {
		/*①创建一个SessionFactory工厂类*/
		SessionFactory sessionFactory = null;
		Configuration configuration = new Configuration().configure();
		//hibernate规定,所有配置或服务，要生效,必须配置或服务注册到一个服务注册类
		ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();		
		sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		/*②通过工厂类开启Session对象*/

		/*③开始事务*/

		/*④执行数据库的操作*/

		/*⑤提交事务*/

		/*⑥关闭Session*/

		/*⑦关闭工厂类*/

		
		
		
		/**包含配置文件里的信息*/
		
		
		
		
	}

}

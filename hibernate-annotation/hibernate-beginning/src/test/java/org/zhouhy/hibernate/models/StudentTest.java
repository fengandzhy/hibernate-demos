package org.zhouhy.hibernate.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

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
        Session session = sessionFactory.openSession();
        /*③开始事务*/
        Transaction transaction = session.beginTransaction();
        /*④执行数据库的操作*/
        Student stu = new Student("sam", "male", new Timestamp(new Date().getTime()));
        session.save(stu);

        /*⑤提交事务*/
        transaction.commit();
        /*⑥关闭Session*/
        session.close();
        /*⑦关闭工厂类*/
        sessionFactory.close();
    }
}

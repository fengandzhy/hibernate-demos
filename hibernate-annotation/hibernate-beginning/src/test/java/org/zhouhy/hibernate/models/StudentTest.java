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
        /*�ٴ���һ��SessionFactory������*/
        SessionFactory sessionFactory = null;
        Configuration configuration = new Configuration().configure();
        //hibernate�涨,�������û����Ҫ��Ч,�������û����ע�ᵽһ������ע����
        ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
        sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        /*��ͨ�������࿪��Session����*/
        Session session = sessionFactory.openSession();
        /*�ۿ�ʼ����*/
        Transaction transaction = session.beginTransaction();
        /*��ִ�����ݿ�Ĳ���*/
        Student stu = new Student("sam", "male", new Timestamp(new Date().getTime()));
        session.save(stu);

        /*���ύ����*/
        transaction.commit();
        /*�޹ر�Session*/
        session.close();
        /*�߹رչ�����*/
        sessionFactory.close();
    }
}

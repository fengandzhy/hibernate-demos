package org.zhouhy.hibernate.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class MembersTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionFactory sessionFactory = null;
    private Session session = null;
    private Transaction transaction = null;
    private SimpleDateFormat sm;

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
        sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
        sm = new SimpleDateFormat("yyyy-mm-dd");
    }

    @Test
    public void saveTest() throws ParseException {
        transaction = session.beginTransaction();
        Member member = new Member();
        member.setName("F");
        member.setTextContent("QY");
        member.setSex("M");
        member.setBirthday(sm.parse("2000-12-23"));
        session.save(member);
        System.out.println(transaction.getStatus());
//        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
//            transaction.commit();
//        }
        transaction.commit();        
    }

    @After
    public void destroy() {
        session.close();
        sessionFactory.close();
    }
}

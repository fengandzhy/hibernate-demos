package org.zhouhy.hibernate.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionImpl;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UserTest {

    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);
    
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
    public void testSave() throws SQLException {
        User user = new User("sam","111111");
        System.out.println(user);
        session.save(user);        
        session.flush();
//        SessionImpl sessionImpl = (SessionImpl) session;
//        System.out.println(sessionImpl.connection().getAutoCommit());
//        throw new SQLException();
        transaction.rollback();
    }

//    @After
//    public void destory() {
//        transaction.commit();
//        session.close();
//        sessionFactory.close();
//    }
}

package org.zhouhy.hibernate.c3p0.models;

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

public class c3p0Test {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionFactory sessionFactory = null;
    private Session session = null;    

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
        sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
    }
    
    @Test
    public void c3p0Test(){
        session.doWork(connection -> logger.info(connection.toString()));
    }
    
    @After
    public void destroy() {
        session.close();
        sessionFactory.close();
    }
}

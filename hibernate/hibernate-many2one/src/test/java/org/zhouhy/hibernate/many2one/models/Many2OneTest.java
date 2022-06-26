package org.zhouhy.hibernate.many2one.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

public class Many2OneTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionFactory sessionFactory = null;
    private Session session = null;
    private SimpleDateFormat sm;

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
        sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
        sm = new SimpleDateFormat("yyyy-MM-dd");
    }

    @After
    public void destroy() {
        session.close();
        sessionFactory.close();
    }
}

package org.zhouhy.hibernate.many2one.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Many2OneTest1 {

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

//    @Test
//    public void testSave1(){
//        Transaction transaction = session.beginTransaction();
//        
//        SysOrg sysOrg = new SysOrg("A1");
//        SysUser sysUser = new SysUser();
//        sysUser.setDept(sysOrg);
//        sysUser.setUserCode("0001");
//        sysUser.setUserName("user1");
//        
//        sysUser.setDept(sysOrg);
//        
//        session.save(sysOrg);
//        session.save(sysUser);
//
//        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
//            transaction.commit();
//        }
//        logger.info("save finished.");
//    }
//
//    @Test
//    public void testGet1(){
//        SysUser sysUser = session.get(SysUser.class,1L);
//        logger.info(sysUser.toString());
//    }
}

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
    }

    @Test
    public void testSave(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");
        try {
            logger.info(user.toString());
            session.save(user);
            transaction.commit();            
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }     
    }    
    
    /**
     * get 方法 是根据 记录的ID 取数据 以下代码虽然执行两次get 方法, 但是对于数据库只产生了一条 select SQL语句
     * 证明第二次执行 get 的时候是从session 缓存中取的. 这就是说session 缓存能够大大减少对数据库的访问.
     * */
    @Test
    public void testGet(){
        User user = session.get(User.class,1L); // 原来的id 是 long 所以这里就是 1l
        logger.info(user.toString());
        User user1 = session.get(User.class,1L);
        logger.info(user1.toString());
    }

    /**
     *  1 这里的flush 方法是清理缓存的操作,执行一系列的SQL语句,但不会提交事务; 它只是发起sql语句,但是并不会真正的影响数据库里的
     *  记录除了 执行到 transaction.commit(); 或者 session.close(); 数据的变化才能在数据库中体现出来.
     *  例如本例，你把user的 password给改了，那么就意味着 缓存里的user和数据库里的记录不一样了, 所以 执行到 session.flush();
     *  的时候会有一条sql语句体现出来, 但是数据库里的记录并没有改变, 直到commit之后     *   
     * */
    @Test
    public void testFlush(){
        transaction = session.beginTransaction();
        User user = session.get(User.class,1L); // 原来的id 是 long 所以这里就是 1l
        user.setPassword("123456");
        session.flush();
        transaction.commit();
    }

    @After
    public void destroy() {        
        session.close();
        sessionFactory.close();
    }
}

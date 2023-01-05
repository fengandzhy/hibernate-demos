package org.zhouhy.hibernate.models;

import org.hibernate.PersistentObjectException;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PersistTest extends AbstractTest{

    /**
     * persist方法跟save方法唯一的区别就是在persist方法之前不能进行id 设置
     * */
    @Test
    public void testPersist(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");
        try {
            logger.info(user.toString());
            assertFalse(session.contains(user));
            session.persist(user);
            assertTrue(session.contains(user));
            logger.info(user.toString());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     *   persist 所保存的对象一定不能有ID值，也就是说它不能保存游离对象.
     *   
     * */
    @Test(expected = PersistentObjectException.class)
    public void testPersistWithIdInAdvance(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");
        user.setId(12L); // 这里设置一个ID, 可能让它变成一个游离对象.
        try {
            logger.info(user.toString());
            session.persist(user);
            logger.info(user.toString()); // 这里你会发现重新分配了一个ID值给它
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
    }

    /**
     * session.persist完了再改某个属性值, 就会执行一条update语句, 也就是说，修改持久状态的对象的某个属性, 就会执行一条update语句
     * */
    @Test
    public void testPersistWithUpdateSomeAttribute(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");
        try {
            logger.info(user.toString());
            session.persist(user);            
            user.setUsername("Frank");
            logger.info(user.toString());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     * session.persist完了再改ID,就会抛出异常 
     * 对于测试来说 (expected = PersistenceException.class) 就是期盼这个东西能丢一个PersistenceException的异常，
     * 但是如果这个方法没有抛出异常, 就会出错，所以一定要把异常抛出去.
     *
     * */
    @Test(expected = PersistenceException.class)
    public void testPersistWithUpdateId(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");
        try {
            logger.info(user.toString());
            session.persist(user);
            user.setId(2L);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw e; // 注意这里一定要把异常抛出,否则就会测试失败.
        }
    }
}

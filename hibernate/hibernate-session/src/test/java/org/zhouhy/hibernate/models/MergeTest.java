package org.zhouhy.hibernate.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class MergeTest extends AbstractTest{

    /**
     * 1. 当作用在一个游离对象时,merge跟update 最大的不同就是merge 对象仍然是游离对象，但是 update 之后对象是持久对象,
     * 正因为merge之后对象 仍然是游离对象，所以要改变它的属性值一定要在merge之前.
     * 
     * 2执行merge之前，它会去执行一条SQL语句，当它发现此刻的对象跟数据库里没啥区别的话，它就不会去执行update语句. 这点跟update 方法有区别. 
     * 对于update方法来说，无论是否相同都会执行update语句.
     * 
     * 
     * */
    @Test
    public void testMergeWithDetachObj1(){
        transaction = session.beginTransaction();
        try {
            User user = session.get(User.class,12L);
            session.evict(user);
            logger.info(user.toString());
            assertFalse(session.contains(user));
            user.setUsername("frank1"); // merge方法也可以改变数据库里的记录, 但是执行完成之后user仍旧是游离对象,还有执行前会发出一条select语句看看user跟数据是否一致，不一致才执行update
            session.merge(user);
            logger.info(user.toString());
            assertFalse(session.contains(user));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
    
    /**
     *  如下 user 是一个游离对象, 执行了merge之后, 它会改变数据库里的值，但对象仍然是游离的
     * */
    @Test
    public void testMergeWithDetachObj2(){
        transaction = session.beginTransaction();
        try {
            User user = new User();
            user.setId(12L);
            session.merge(user);
            assertFalse(session.contains(user));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     * 当这个ID数据库里没有时，它会执行一条insert语句.执行完之后，这个对象还是新建状态, 这个对象的ID值并没有改变, 但是数据库里会多一条记录
     * 
     * */
    @Test
    public void testMergeWithTransientInstance(){
        transaction = session.beginTransaction();
        try {
            User user = new User();
            user.setId(15L);
            user.setPassword("234");
            user.setUsername("ffk");
            session.merge(user);
            assertFalse(session.contains(user));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     * 当这个对象是持久对象时，merge方法失效. 因为没有看到它执行select语句.
     * 
     * */
    @Test
    public void testMergeWithPersistentInstance(){
        transaction = session.beginTransaction();
        try {
            User user = session.get(User.class,40L);            
            user.setPassword("23443");
            user.setUsername("ffk3");
            session.merge(user);
//            assertFalse(session.contains(user));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
}

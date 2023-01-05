package org.zhouhy.hibernate.models;

import org.hibernate.NonUniqueObjectException;

import org.junit.Test;

public class SaveOrUpdateTest extends AbstractTest{
    
    /**
     * 对于一个游离状态的对象 saveOrUpdate 会表现出update的特性执行一条update语句
     * 
     * 当游离对象时执行update方法时一定会执行一条update语句.
     * 
     * */
    @Test
    public void testSaveOrUpdateFromDetachedToPersistent(){
        transaction = session.beginTransaction();
        User user = new User("abc","2345");
        user.setId(8L);
        try {
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对于一个临时状态的对象 saveOrUpdate 会表现出save的特性执行一条insert语句
     * */
    @Test
    public void testSaveOrUpdateFromTransientToPersistent(){
        transaction = session.beginTransaction();
        User user = new User();
        user.setPassword("222");
        user.setUsername("AB");
        try {
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在一个session 缓存中绝对不能出现两个id值相同的对象，否则报错
     * */
    @Test(expected = NonUniqueObjectException.class)
    public void testSaveOrUpdateDuplicateId(){
        transaction = session.beginTransaction();
        @SuppressWarnings("unused") User user = session.get(User.class,8L);
        User user1 = new User();
        user1.setId(8L);
        try {
            session.saveOrUpdate(user1);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

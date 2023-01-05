package org.zhouhy.hibernate.models;


import org.junit.Test;

import javax.persistence.OptimisticLockException;

import static org.junit.Assert.assertFalse;

public class UpdateTest extends AbstractTest{

    /**
     * 1 什么是游离状态的对象, 字面上讲是session 关闭了或者clear之后, 原来持久状态的对象就变成游离状态了. 事实上
     * 只要一个对象的ID 在数据库里有相同 ID 的记录 不管是这个对象是new 出来的 还是持久状态转变而来的, 它都可以是游离状态
     *
     * 2. session.update(user); 语句可以把一个游离状态的对象转换成持久状态, 它会在session.flush()的时候执行一个update语句
     * 但是这个update 语句执行成功的前提是 user 的ID 必须在数据库里存在, 如果ID 在数据库里不存在就会报出错误 
     * 
     * 3. 这里的游离对象是从数据库里取到的. 
     * */
    @Test
    public void testUpdateWithDetachObj1(){
        transaction = session.beginTransaction();                
        try {
            User user = session.get(User.class,12L);
            session.evict(user);
            assertFalse(session.contains(user));
            user.setUsername("fek");
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     * 这里是new出来的游离对象, 由于只有一个id,所以其他值就为空, 它也可以成功更新.
     * */
    @Test
    public void testUpdateWithDetachObj2(){
        transaction = session.beginTransaction();
        try {
            User user = new User();
            user.setId(12L);            
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     * 这里的ID=6就是数据库里不存在的, 所以它执行update的时候会报错.
     * 
     * */
    @Test(expected = OptimisticLockException.class)
    public void testUpdateWithAnIdNotExistsInDB(){
        transaction = session.beginTransaction();
        try {
            User user = new User();
            user.setId(6L);
            user.setUsername("ABC");
            user.setPassword("32");
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
    }

    /**
     * 当保存一个新建对象的时候, 它也会报错，所以update只会把游离对象弄成持久状态对象. 
     * 游离对象是，必须ID有值且该值存在于数据库中
     *
     * */
    @Test(expected = OptimisticLockException.class)
    public void testUpdateWithNewInstance(){
        transaction = session.beginTransaction();
        try {
            User user = new User();            
            user.setUsername("ABC");
            user.setPassword("32");
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
    }

    /**
     * 另外非常要注意的是, update语句本身所触发的就是一条update语句, 所以当user 被执行session.update(user); 再次变成持久状态对象时
     * 再去改user的其他属性, 它不会重复执行一条update语句. 它只会用一条update语句改变所有. 这点跟save方法不一样. save方法是
     * 当你执行了save变成持久对象后, 再去改变对象的某个属性, 它会执行一条update语句. 
     * 
     * */
    @Test
    public void testUpdateWithSomeAttributeChanged(){
        transaction = session.beginTransaction();
        try {
            User user = new User();
            user.setId(12L);
            session.update(user);
            user.setPassword("2222");                    
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
}

package org.zhouhy.hibernate.models;

import org.junit.Test;

import javax.persistence.OptimisticLockException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteTest extends AbstractTest{

    /**
     * delete 方法可以把一个游离对象直接转变成为一个临时对象. 在session缓存中被清除, 在数据库的相关记录也别删除，
     * 它会在 flush的时候执行一条delete语句.
     *
     * */
    @Test
    public void testDeleteFromDetachedToTransient(){
        transaction = session.beginTransaction();
        User user = new User();
        user.setId(27L);
        try {
            session.delete(user);// 这里的user是一个临时对象,它有ID值，执行完delete它就是临时对象了
            user.setId(1L); // 这里能修改ID,证明之前的user已经是一个临时对象了,但是这里赋值了, 就有可能是一个游离对象.
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果删除一个数据库里没有ID的记录,就会抛出异常.
     * */
    @Test(expected = OptimisticLockException.class)
    public void testDeleteWithIdNotExistsInDB(){
        transaction = session.beginTransaction();
        User user = new User();
        user.setId(6L);
        try {
            session.delete(user);            
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * delete 也会把一个持久状态转换成一个临时状态, 临时状态的对象它是没有ID的或者ID为的
     * */
    @Test
    public void testDeleteFromPersistentToTransient(){
        transaction = session.beginTransaction();
        User user = session.get(User.class,32L);
        try {
            assertTrue(session.contains(user));
            session.delete(user);
            assertFalse(session.contains(user));
            logger.info(user.toString());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

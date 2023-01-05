package org.zhouhy.hibernate.models;

import org.junit.Test;

import static org.junit.Assert.*;


public class ClearTest extends AbstractTest{

    /**
     * clear 只是清理缓存, 它并不负责同步数据库, 所以它即便改了password 它也不去执行update语句
     * 另外 执行session.clear(); 之后再去执行 User user1 = session.get(User.class,1L); 它会再次发起sql语句
     * 所以由此可以证明缓存被清除了.
     * */
    @Test
    public void testClear(){
        transaction = session.beginTransaction();
        User user = session.get(User.class,1L); // 原来的id 是 long 所以这里就是 1l
        logger.info(user.toString());
        user.setPassword("1234567");
        assertTrue(session.contains(user));
        session.clear();
        assertFalse(session.contains(user));
        user = session.get(User.class,1L);
        logger.info(user.toString());
        transaction.commit();
    }
}

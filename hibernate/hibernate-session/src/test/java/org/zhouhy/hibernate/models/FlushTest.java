package org.zhouhy.hibernate.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FlushTest extends AbstractTest{

    /**
     *  1 这里的flush 方法是清理缓存的操作,执行一系列的SQL语句,但不会提交事务; 它只是发起sql语句,但是并不会真正的影响数据库里的
     *  记录除了 执行到 transaction.commit(); 或者 session.close(); 数据的变化才能在数据库中体现出来.
     *  例如本例，你把user的 password给改了，那么就意味着 缓存里的user和数据库里的记录不一样了, 所以 执行到 session.flush();
     *  的时候会有一条 update sql语句体现出来, 但是数据库里的记录并没有改变, 直到commit之后     *   
     * */
    @Test
    public void testFlush(){
        transaction = session.beginTransaction();
        User user = session.get(User.class,1L); // 原来的id 是 long 所以这里就是 1l
        user.setPassword("123456");
        assertTrue(session.contains(user));        
        session.flush();        
        transaction.commit();
    }
}

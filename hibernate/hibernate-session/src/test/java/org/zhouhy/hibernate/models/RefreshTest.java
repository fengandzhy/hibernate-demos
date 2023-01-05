package org.zhouhy.hibernate.models;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RefreshTest extends AbstractTest{

    /**
     * 这里的 session.refresh(user); 方法就是强制让session里面的对象跟数据库保持一一致，如果不一致，则更新session里面的对象 
     * 而不是更新数据库.
     * 它这个 flush是针对的全体 session里的缓存对象 而这个 refresh 只是针对的某一个对象.
     *  
     *  2. refresh 方法也不清空缓存
     * */
    @Test
    public void testRefresh(){
        transaction = session.beginTransaction();
        User user = session.get(User.class,1L); // 原来的id 是 long 所以这里就是 1l
        user.setPassword("123456");
        assertTrue(session.contains(user));
        session.refresh(user);
        assertTrue(session.contains(user));
        logger.info(user.getPassword());
        transaction.commit();
    }
}

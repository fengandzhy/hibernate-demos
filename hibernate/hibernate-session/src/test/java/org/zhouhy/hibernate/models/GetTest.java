package org.zhouhy.hibernate.models;

import org.junit.Test;

public class GetTest extends AbstractTest{
    
    
    @Test
    public void testGet(){
        User user = session.get(User.class,1L); // 原来的id 是 long 所以这里就是 1l
        logger.info(user.toString());
        User user1 = session.get(User.class,1L); // 这里并没有执行sql语句而是从session缓存中取值
        logger.info(user1.toString());
    }

    @Test
    public void testGetWithUpdate(){
        transaction = session.beginTransaction();
        User user = session.get(User.class,1L); // 原来的id 是 long 所以这里就是 1l
        logger.info(user.toString());
        user.setPassword("1234567");
        user = session.get(User.class,1L); //这里是从缓存中取,所以取到的password还是1234567
        logger.info(user.toString());
        transaction.commit();
    }
}

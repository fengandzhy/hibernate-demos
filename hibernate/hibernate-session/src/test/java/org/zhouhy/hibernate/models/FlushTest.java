package org.zhouhy.hibernate.models;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FlushTest extends AbstractTest{

    /**
     *  1 这里的flush 方法是清理缓存的操作,执行一系列的SQL语句,但不会提交事务; 它只是发起sql语句,但是并不会真正的影响数据库里的
     *  记录除了 执行到 transaction.commit(); 或者 session.close(); 数据的变化才能在数据库中体现出来.
     *  例如本例，你把user的 password给改了，那么就意味着 缓存里的user和数据库里的记录不一样了, 所以 执行到 session.flush();
     *  的时候会有一条 update sql语句体现出来, 但是数据库里的记录并没有改变, 直到commit之后 
     *  
     *  
     *  2 清理缓存和清空缓存不一样， 
     *  Session具有一个缓存，位于缓存中的对象处于持久化状态，它和数据库中的相关记录对应，Session能够在某些时间点，
     *  按照缓存中持久化对象的属性变化来同步更新数据库，这一过程被称为清理缓存.
     *  
     *  3 清空缓存是指，把对象从缓存中拿走.当调用session.evict(customer); 或者session.clear(); 
     *  或者session.close()方法时，Session的缓存被清空。
     * */
    @Test
    public void testFlush(){
        transaction = session.beginTransaction();
        User user = session.get(User.class,1L); // 原来的id 是 long 所以这里就是 1l
        user.setPassword("123456");
        assertTrue(session.contains(user));        
        session.flush(); // 这里清理缓存并不清空缓存
        assertTrue(session.contains(user));
        transaction.commit();
    }
}

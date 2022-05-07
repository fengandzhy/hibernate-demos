package org.zhouhy.hibernate.models;

import org.junit.Test;

public class UserTest extends AbstractTest{
    
    /**
     * 1. 当一个对象执行了save方法之后又这样几个变化
     *  1) 它被纳入到了session 的缓存, 受session 管理
     *  2) hibernate 会给它一个id 这个id就是它插入到数据库之后的id, 它如果原先没有id 它会给它生成一个id 但如果有id 了 它还会重新给它分配一个id 
     * 2. 另外save方法是否发起insert 语句取决于这个 主键生成方式 比方说如果主键生成的方式是native, 那么它会在调用save 方法直接发出insert
     * 如果主键生成的方式是uuid, 执行save方法的时候不会发起insert 只有在flush 方法清理session缓存的时候才会发起insert
     * 3. 在调用了save之后, 也就是对象已经是持久状态了, 就不能在setId 了，如果 setId 会报错
     * 4. 在save方法之后，再去修改其他，例如用户名，user.setUsername("Frank"); 虽然user对象此刻未表现在数据库, 
     * 但是你修改了持久状态的某个属性(这个属性绝对不能是ID), 它仍然要去执行一个update语句 
     * 
     * */
    @Test
    public void testSave(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");
//        user.setId(2L);
        try {
            logger.info(user.toString());
            session.save(user);
//            user.setUsername("Frank");
            logger.info(user.toString());
            transaction.commit();            
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }     
    }
    
    /**
     * 1. 注意这里的clear方法记录插入了但是没有执行修改, 这是因为当你执行session.save(user); 它已经发出了insert 语句
     * 这是因为这里的 id 生成策略是 native. 而 session.clear(); 发生在发出insert 语句之后, 所以当commit的时候就体现在了数据库里. 
     * 但是这的 user.setUsername("Frank"); 所引发的update 语句却是在commit之时发出的, 它发生在session.clear();之后 所以
     * 它不被执行. 因为此时 session 已经被清空.  
     * */
    @Test
    public void testSaveWithClear(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");
        try {
            logger.info(user.toString());
            session.save(user);
            user.setUsername("Frank");
            session.clear();
            logger.info(user.toString());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
    
    /**
     * get 方法 是根据 记录的ID 取数据 以下代码虽然执行两次get 方法, 但是对于数据库只产生了一条 select SQL语句
     * 证明第二次执行 get 的时候是从session 缓存中取的. 这就是说session 缓存能够大大减少对数据库的访问.
     * */
    @Test
    public void testGet(){
        User user = session.get(User.class,1L); // 原来的id 是 long 所以这里就是 1l
        logger.info(user.toString());
        User user1 = session.get(User.class,1L);
        logger.info(user1.toString());
    }

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
        session.flush();
        transaction.commit();
    }

    /**
     * 这里的 session.refresh(user); 方法就是强制让session里面的对象跟数据库保持一一致，如果不一致，则更新session里面的对象 
     * 而不是更新数据库.
     * 它这个 flush是针对的全体 session里的缓存对象 而这个 refresh 只是针对的某一个对象.
     * 
     * */
    @Test
    public void testRefresh(){
        transaction = session.beginTransaction();
        User user = session.get(User.class,1L); // 原来的id 是 long 所以这里就是 1l
        user.setPassword("123456");
        session.refresh(user);
        logger.info(user.getPassword());
        transaction.commit();
    }

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
        session.clear();
        user = session.get(User.class,1L);
        logger.info(user.toString());
        transaction.commit();
    }    
}

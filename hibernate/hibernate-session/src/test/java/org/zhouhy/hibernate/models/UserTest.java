package org.zhouhy.hibernate.models;

import org.hibernate.Session;
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
    public void testSessionCache(){
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

    /**
     * 1 什么是游离状态的对象, 字面上讲是session 关闭了或者clear之后, 原来持久状态的对象就变成游离状态了. 事实上
     * 只要一个对象的ID 在数据库里有相同 ID 的记录 不管是这个对象是new 出来的 还是持久状态转变而来的, 它都可以是游离状态
     * 
     * 2. session.update(user); 语句可以把一个游离状态的对象转换成持久状态, 它会在session.flush()的时候执行一个update语句
     * 但是这个update 语句执行成功的前提是 user 的ID 必须在数据库里存在, 如果ID 在数据库里不存在就会报出错误 
     * */
    @Test
    public void testUpdate(){
        Session session = sessionFactory.openSession();
        User user = session.get(User.class,7L);
        session.close();        
//        user.setId(8L);
        user.setUsername("fek");
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();        
        try {
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }
    }

    /**
     * 这里的 user 虽然是 new 出来的, 但是它的ID值是 6 而对应的数据库里也有ID 为6 的记录， 不管其他部分是否相等, 
     * 那么此刻的 user 就是一个游离对象
     * 
     * 但是如果这里的user 的ID是数据库里没有的, 那就会报错
     * 
     * 另外非常要注意的是, update语句本身所触发的就是一条update语句, 所以当user 被执行session.update(user); 再次变成持久状态对象时
     * 再去改user的其他属性, 它不会重复执行一条update语句. 它只会用一条update语句改变所有. 这点跟save方法不一样. save方法是
     * 当你执行了save变成持久对象后, 再去改变对象的某个属性, 它会执行一条update语句. 
     * 
     * 持久状态下的对象绝对不能修改ID
     * */
    @Test
    public void testUpdateWithNewInstance(){
        transaction = session.beginTransaction();
        User user = new User("122","3wqq25");
        user.setId(6L);
        try {
            session.update(user);
            user.setUsername("pky");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete 方法可以把一个游离对象直接转变成为一个临时对象. 在session缓存中被清除, 在数据库的相关记录也别删除，
     * 它会在 flush的时候执行一条delete语句.
     * */
    @Test
    public void testDeleteFromDetachedToTransient(){
        transaction = session.beginTransaction();
        User user = new User();
        user.setId(6L);
        try {
            session.delete(user);
            user.setId(1L);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete 也会把一个持久状态转换成一个临时状态
     * */
    @Test
    public void testDeleteFromPersistentToTransient(){
        transaction = session.beginTransaction();
        User user = session.get(User.class,7L);        
        try {
            session.delete(user);
            user.setId(1L);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对于一个游离状态的对象 saveOrUpdate 会表现出update的特性执行一条update语句
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
    @Test
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
        }
    }

    /**
     * persist方法跟save方法唯一的区别就是在persist方法之前不能进行id 设置
     * */
    @Test
    public void testPersist(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");
//        user.setId(10L);
        try {            
            session.persist(user);
            user.setUsername("Frank");
            logger.info(user.toString());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
    
    /**
     * 1.get方法会立即加载对象，发起sql语句
     * 2.load方法不会立即加载对象，而是当加载的对象被使用的时候才会去加载对象，发起sql语句
     * 3.get方法返回的是就是对象本身
     * 4.load方法返回值不是user的对象本身而是user对象的一个代理
     * 5.get方法，如果查询的数据在数据库中没有对应的id的记录值, get方法返回null, 不报异常, 
     * 6.load方法，当使用加载对象的时候, 代理对象才加载真正的对象并发起sql,这时才发现查不到对象,所以就只能报出异常了!
     * 
     * */
    @Test
    public void testGet() {
        User user = session.get(User.class, 1L);
//        logger.info(user.getClass().getName());        
    }

    @Test
    public void testLoad() {
        User user = session.load(User.class, 1L);
//        logger.info(user.getClass().getName());         
    }
        
}

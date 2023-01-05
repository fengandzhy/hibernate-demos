package org.zhouhy.hibernate.models;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;
import java.util.Date;

public class SaveTest extends AbstractTest{

    @Rule
    public ExpectedException thrown= ExpectedException.none();

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
        try {
            logger.info(user.toString());
            session.save(user);
            logger.info(user.toString());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     * 这里预设ID是没有用的, hibernate 会根据数据库里的ID 会自动分配一个值
     * 也就是说save方法可以去保存一个游离对象， 但是它会无视这个游离对象的ID, 会把它当成一个新建对象重新分配一个ID给它
     * */
    @Test
    public void testSaveWithIdInAdvance(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");
        user.setId(12L); // 这里设置一个ID, 可能让它变成一个游离对象.
        try {
            logger.info(user.toString());
            session.save(user);            
            logger.info(user.toString()); // 这里你会发现重新分配了一个ID值给它
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     * session.save完了再改某个属性值, 就会执行一条update语句, 也就是说，修改持久状态的对象的某个属性, 就会执行一条update语句
     * */
    @Test
    public void testSaveWithUpdateSomeAttribute(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");        
        try {
            logger.info(user.toString());
            session.save(user);
            user.setUsername("Frank");
            logger.info(user.toString());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     * session.save完了再改ID,就会抛出异常 
     * 对于测试来说 (expected = PersistenceException.class) 就是期盼这个东西能丢一个PersistenceException的异常，
     * 但是如果这个方法没有抛出异常, 就会出错，所以一定要把异常抛出去.
     * 
     * */
    @Test(expected = PersistenceException.class)
    public void testSaveWithUpdateId(){
        transaction = session.beginTransaction();
        User user = new User("sam","111111");        
        try {
            logger.info(user.toString());            
            session.save(user);
            user.setId(2L);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw e; // 注意这里一定要把异常抛出,否则就会测试失败.
        }
    }    

    /**
     * 1. 注意这里的clear方法记录插入了但是没有执行修改, 这是因为当你执行session.save(user); 它已经发出了insert 语句
     * 这是因为这里的 id 生成策略是 native. 而 session.clear(); 发生在发出insert 语句之后, 所以当commit的时候就体现在了数据库里. 
     * 但是这的 user.setUsername("Frank"); 所引发的update 语句却是在commit之时发出的, 它发生在session.clear();之后 所以
     * 它不被执行. 因为此时 session 已经被清空.  
     * */
    @Test
    public void testSaveWithClear1(){
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
     *  这里的session.save(stu);并不发出insert语句, 这里的主键生成是uuid, insert语句是在transaction.commit();之时发出的, 此时已经执行过session.clear();
     *  session缓存已经被清空, 所以这里不发出insert 语句, 这里也就不会向数据库插入记录了.
     * */
    @Test
    public void testSaveWithClear2(){
        transaction = session.beginTransaction();
        Student stu = new Student("sam","male",new Date());
        try {
            logger.info(stu.toString());
            session.save(stu);
            session.clear();
            logger.info(stu.toString());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
}

package org.zhouhy.hibernate.models;

import org.junit.Test;
import java.util.Date;

public class StudentTest extends AbstractTest{

    @Test
    public void testSave(){
        transaction = session.beginTransaction();
        Student stu = new Student("sam","male",new Date());
        try {
            logger.info(stu.toString());
            session.save(stu);
            stu.setName("Frank");
            logger.info(stu.toString());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     *  这里的session.save(stu);并不发出insert语句, insert语句是在transaction.commit();之时发出的, 此时已经执行过session.clear();
     *  session缓存已经被清空, 所以这里不发出insert 语句, 这里也就不会向数据库插入记录了.
     * */
    @Test
    public void testSaveWithClear(){
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

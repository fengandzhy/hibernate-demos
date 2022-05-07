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
            logger.info(stu.toString());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }   
}

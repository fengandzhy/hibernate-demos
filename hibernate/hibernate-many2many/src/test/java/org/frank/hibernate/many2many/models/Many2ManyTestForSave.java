package org.frank.hibernate.many2many.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import javax.persistence.PersistenceException;


public class Many2ManyTestForSave extends Many2ManyTest{
    
    
    /**
     * inverse=true 在Course, 所以只有Teacher 维持对表t_teacher_course更新
     * 
     * */
    @Test
    public void testSaveForCourseInverse1(){
        Transaction transaction = session.beginTransaction();
        Teacher t1 = new Teacher("a1","t1");
        Teacher t2 = new Teacher("a2", "t2");
        Course c1 = new Course("b1","c1");
        Course c2 = new Course("b2","c2");
        t1.getCourses().add(c1);
        t2.getCourses().add(c2);
//        t1.getCourses().add(c2);
//        t2.getCourses().add(c1);
        
        session.save(c1);
        session.save(c2);
        session.save(t1);
        session.save(t2);
        
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * inverse=true 在Course, 所以只有Teacher 维持对表t_teacher_course更新, 而这里只设置了Course 到 teacher 的关联，因此
     * 无法更新中间表.
     *
     * */
    @Test
    public void testSaveForCourseInverse2(){
        Transaction transaction = session.beginTransaction();
        Teacher t1 = new Teacher("a1","t1");
        Teacher t2 = new Teacher("a2", "t2");
        Course c1 = new Course("b1","c1");
        Course c2 = new Course("b2","c2");
        c1.getTeachers().add(t1);
        c2.getTeachers().add(t2);
        c1.getTeachers().add(t2);
        c2.getTeachers().add(t1);

        session.save(c1);
        session.save(c2);
        session.save(t1);
        session.save(t2);

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 当双方都不设置inverse=true的时候, 这个时候两方都要维护t_teacher_course表, 如果这时彼此双方都设置了关联关系的话，就会抛出异常,
     * 所以，只能去掉一方的关联.
     * 
     * 
     * */
    @Test(expected = PersistenceException.class)
    public void testSaveForNoInverse(){
        Transaction transaction = session.beginTransaction();
        Teacher t1 = new Teacher("a1","t1");
        Teacher t2 = new Teacher("a2", "t2");
        Course c1 = new Course("b1","c1");
        Course c2 = new Course("b2","c2");
        
        c1.getTeachers().add(t1);
        c2.getTeachers().add(t2);
        c1.getTeachers().add(t2);
        c2.getTeachers().add(t1);

        t1.getCourses().add(c1);
        t2.getCourses().add(c2);
        t1.getCourses().add(c2);
        t2.getCourses().add(c1);

        session.save(c1);
        session.save(c2);
        session.save(t1);
        session.save(t2);

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

package org.frank.hibernate.many2many.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class Many2ManyTestForCascade extends Many2ManyTest{
    
    /**
     * 当teacher 的cascade=save-update 的时候, 保存或者更新一个teacher实例，它会级联保存与之关联的 course 实例.
     * 同理 当course 的cascade=save-update 的时候, 保存或者更新一个course实例，它会级联保存与之关联的 teacher 实例.
     * 
     * 此例子中虽然只保存了c1 但是 Course 的 cascade=save-update 所以它会保存关联的 t1,t2, 而 Teacher 的 cascade=save-update
     * 所以保存t1,t2的时候会保存它们所关联的Course 对象 也就是c2. 
     * 
     * */
    @Test
    public void testSaveForBothSaveUpdate(){
        Transaction transaction = session.beginTransaction();
        Teacher t1 = new Teacher("a1","t1");
        Teacher t2 = new Teacher("a2", "t2");
        Course c1 = new Course("b1","c1");
        Course c2 = new Course("b2","c2");
        
        t1.getCourses().add(c1);
        t2.getCourses().add(c2);
        t1.getCourses().add(c2);
        t2.getCourses().add(c1);

        c1.getTeachers().add(t1);
        c2.getTeachers().add(t2);
        c1.getTeachers().add(t2);
        c2.getTeachers().add(t1);

        session.save(c1);        

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }    
}

package org.frank.hibernate.many2many.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import javax.persistence.PersistenceException;

public class Many2ManyTestForDelete extends Many2ManyTest{
    
    /**
     * 当cascade = none 的时候, 删除t1 它会自动删除关联表里的相关记录.
     * */
    @Test
    public void testDeleteTeacherWithNoCascade(){
        Transaction transaction = session.beginTransaction();
        Teacher t1 = session.get(Teacher.class,14L);
        session.delete(t1);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 当cascade = delete 的时候, 删除t1 它会去试图删除 t_course 表里的相关内容, 这就很容易出错, 
     * 因为关联着15 teacher 的 course 可能也被其他teacher 关联. 除非就是你管理的这个course 没有被其他teacher 关联.
     * 
     * 打个比方就是ID=15 的teacher，它又两个course 管理, 分别是15 和 16, 但是这两个course 又关联了ID = 16 的 teacher, 
     * 所以删除时就会报错.  
     * 
     * */
    @Test(expected = PersistenceException.class)
    public void testDeleteTeacherWithDeleteCascade(){
        Transaction transaction = session.beginTransaction();
        Teacher t1 = session.get(Teacher.class,15L);
        session.delete(t1);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

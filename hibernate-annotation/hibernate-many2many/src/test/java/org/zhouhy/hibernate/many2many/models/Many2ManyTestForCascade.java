package org.zhouhy.hibernate.many2many.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class Many2ManyTestForCascade extends Many2ManyTest{
    
    /**
     * 只要有 CascadeType.PERSIST 就不能用persist 保存与之关联的游离对象. 
     * save 在这里不会报错, 由于两边都是 @JoinTable 所以两边都参与维护中间表, 这里只设置了User的关联 u1.getRoles().add(r1); 
     * 所以也会更新中间表.
     * 
     * */
    @Test
    public void testCascadePersistAndMergeToSaveDetachRole(){
        Transaction transaction = session.beginTransaction();
        User u1 = new User();
        u1.setUsername("a7");
        u1.setPassword("1");
        Role r1 = session.get(Role.class,1L);
        session.evict(r1);
        u1.getRoles().add(r1);
        session.persist(u1);
//        session.save(u1);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

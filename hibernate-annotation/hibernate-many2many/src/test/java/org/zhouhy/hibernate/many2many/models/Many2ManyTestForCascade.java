package org.zhouhy.hibernate.many2many.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class Many2ManyTestForCascade extends Many2ManyTest{
    
    @Test
    public void testCascadePersistAndMergeToSaveDetachRole(){
        Transaction transaction = session.beginTransaction();
        User u1 = new User();
        u1.setUsername("a5");
        u1.setPassword("1");
        Role r1 = session.get(Role.class,1L);
        session.evict(r1);
        u1.getRoles().add(r1);
        session.save(u1);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

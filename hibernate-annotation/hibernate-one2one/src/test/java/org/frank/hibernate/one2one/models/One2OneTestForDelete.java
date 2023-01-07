package org.frank.hibernate.one2one.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class One2OneTestForDelete extends One2OneTest{
    
    /**
     * 由于在Card类里配置了 cascade = CascadeType.ALL 所以当删除card 时先删除的是person 再删除card
     * 
     * cascade 配置在没有外键的地方.
     * */
    @Test
    public void testDelete(){
        Transaction transaction = session.beginTransaction();
        Card card = session.get(Card.class,4L);
        session.remove(card);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

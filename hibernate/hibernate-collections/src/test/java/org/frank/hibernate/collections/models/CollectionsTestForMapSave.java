package org.frank.hibernate.collections.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class CollectionsTestForMapSave extends CollectionsTest{
    
    @Test
    public void testSave(){
        Transaction transaction = session.beginTransaction();
        
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

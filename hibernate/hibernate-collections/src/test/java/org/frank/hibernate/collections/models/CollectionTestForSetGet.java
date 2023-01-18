package org.frank.hibernate.collections.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class CollectionTestForSetGet extends CollectionsTest{
    
    @Test
    public void testGetForSetOrderBy(){
        SysUser sysUser = session.get(SysUser.class,1L);
        logger.info(sysUser.toString());
    }

    /**
     * 这种方式可以修改
     * */
    @Test
    public void testGetForSetAndUpdate(){
        Transaction transaction = session.beginTransaction();
        SysUser sysUser = session.get(SysUser.class,1L);
        sysUser.getAddressSet().add("广州");
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

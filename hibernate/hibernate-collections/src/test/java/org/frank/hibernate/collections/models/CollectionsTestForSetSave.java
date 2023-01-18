package org.frank.hibernate.collections.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class CollectionsTestForSetSave extends CollectionsTest{
    
    @Test
    public void testSaveSet(){
        Transaction transaction = session.beginTransaction();
        Set<String> address = new HashSet<>();
        address.add("北京");
        address.add("上海");
        
        SysUser sysUser = new SysUser();
        sysUser.setAddressSet(address);
        sysUser.setName("李刚");
        session.save(sysUser);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

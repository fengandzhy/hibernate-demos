package org.frank.hibernate.collections.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;
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
    
    /**
     * 这种简单的集合类映射, 只要设置了跟原有主类的关联关系, 就可以直接实现 delete 和 insert 了
     * 
     * */
    @Test
    public void testDelete(){
        Transaction transaction = session.beginTransaction();
        SysUser sysUser = session.get(SysUser.class, 1L);
        Date date1 = new GregorianCalendar(2014, 2, 11).getTime();
        Set<Date> dateSet = new HashSet<>();
        dateSet.add(date1);
        sysUser.setDateSet(dateSet);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

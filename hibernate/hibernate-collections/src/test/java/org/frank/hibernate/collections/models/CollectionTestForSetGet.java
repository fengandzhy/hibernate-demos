package org.frank.hibernate.collections.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CollectionTestForSetGet extends CollectionsTest{
    
    @Test
    public void testGetForSetOrderBy(){
        SysUser sysUser = session.get(SysUser.class,1L);
        logger.info(sysUser.toString());
    }

    /**
     * 以下执行一条 insert 语句 插入到 user_address_set 表里
     * */
    @Test
    public void testGetForSetAndUpdateAddNewAddressToSysUser(){
        Transaction transaction = session.beginTransaction();
        SysUser sysUser = session.get(SysUser.class,1L);
        sysUser.getAddressSet().add("广州");
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 以下执行一条 insert 语句 插入到 user_address_set 表里, 关联的是 sysUser2
     * */
    @Test
    public void testGetForSetAndUpdateAddNewAddressToAnotherSysUser(){
        Transaction transaction = session.beginTransaction();
        SysUser sysUser1 = session.get(SysUser.class,1L);
        SysUser sysUser2 = session.get(SysUser.class,2L);
        Set<String> address1 = sysUser1.getAddressSet();
        Set<String> address2 = sysUser2.getAddressSet();
        Iterator<String> iterator = address1.iterator();
        while(iterator.hasNext()){
            String add = iterator.next();
            if(add.equals("广州")){
                address2.add(add);
                break;
            }
        }
        
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 以下执行一条 delete 语句.
     * */
    @Test
    public void testGetForSetAndUpdateRemoveAddressForSysUser(){
        Transaction transaction = session.beginTransaction();
        SysUser sysUser1 = session.get(SysUser.class,1L);        
        Set<String> address1 = sysUser1.getAddressSet();        
        Iterator<String> iterator = address1.iterator();
        while(iterator.hasNext()){
            String add = iterator.next();
            if(add.equals("广州")){
                iterator.remove();
                break;
            }
        }
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 这会执行一条delete 删除user_address_set 和 sysUser1 关联的所有项目, 然后在执行两条insert 语句插入user_addressSet
     * 
     * */
    @Test
    public void testGetForSetAndUpdateChangeAddressSetForSysUser(){
        Transaction transaction = session.beginTransaction();
        SysUser sysUser1 = session.get(SysUser.class,1L);
        Set<String> address1 = new HashSet<>();
        address1.add("南京");
        address1.add("苏州");
        sysUser1.setAddressSet(address1);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 这会执行一条delete 删除user_address_set 和 sysUser1 关联的所有项目, 然后然后再执行一条delete 语句删除t_sys_user表中的相应内容.
     *
     * */
    @Test
    public void testGetForSetAndUpdateRemoveSysUser(){
        Transaction transaction = session.beginTransaction();
        SysUser sysUser1 = session.get(SysUser.class,1L);
        session.delete(sysUser1);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

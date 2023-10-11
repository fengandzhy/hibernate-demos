package org.frank.hibernate.collections.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollectionTestForList extends CollectionsTest{
    
    @Test
    public void testSaveForList(){
        Transaction transaction = session.beginTransaction();
        List<String> list = new ArrayList<>();
        list.add("广州");
        list.add("深圳");
        Stuff stuff = new Stuff();
        stuff.setName("张三");
        stuff.setAddressList(list);
        session.save(stuff);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * list 默认其实是懒加载. 以下代码直接给 t_stuff_address_list 增加一条记录
     * */
    @Test
    public void testGetForList(){
        Transaction transaction = session.beginTransaction();
        Stuff stuff = session.get(Stuff.class,1L);
        stuff.getAddressList().add("深圳");
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    @Test
    public void testRemoveItemFromList(){
        Transaction transaction = session.beginTransaction();
        Stuff stuff = session.get(Stuff.class,1L);
        Iterator<String> iter = stuff.getAddressList().iterator();
        while(iter.hasNext()){
            if(iter.next().equals("深圳")){
                iter.remove();
            }
        }
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

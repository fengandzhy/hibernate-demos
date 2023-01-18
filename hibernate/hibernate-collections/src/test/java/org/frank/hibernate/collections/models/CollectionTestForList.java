package org.frank.hibernate.collections.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.ArrayList;
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
     * list 默认其实是懒加载.
     * */
    @Test
    public void testGetForList(){
        Stuff stuff = session.get(Stuff.class,1L);
    }
}

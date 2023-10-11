package org.frank.hibernate.collections.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CollectionsTestForMapSave extends CollectionsTest{
    
    @Test
    public void testSave(){
        Transaction transaction = session.beginTransaction();
        Map<String, String> addressMap = new HashMap<>();
        addressMap.put("0001","上海");
        addressMap.put("0002","广州");
        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setAddressMap(addressMap);
        session.save(employee);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

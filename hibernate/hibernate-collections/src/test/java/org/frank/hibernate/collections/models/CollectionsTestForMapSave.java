package org.frank.hibernate.collections.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
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
    
    /**
     * 以下会删除 t_employee_address_map 中的一条记录 
     * */
    @Test
    public void testDelete(){
        Transaction transaction = session.beginTransaction();
        Employee employee = session.get(Employee.class, 1L);
        Map<String, String> map = employee.getAddressMap();
        Iterator<Map.Entry<String,String>> entryIterator = map.entrySet().iterator();
        while(entryIterator.hasNext()){
            if(entryIterator.next().getKey().equals("0001")){
                entryIterator.remove();
            }
        }        
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

package org.frank.hibernate.component.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;


import java.util.GregorianCalendar;

public class ComponentTestForSave extends ComponentTest{
    
    @Test
    public void testSave(){
        Transaction transaction = session.beginTransaction();
        StudentInfo info = new StudentInfo();
        info.setAddress("Address");
        info.setBirthday( new GregorianCalendar(2014, 2, 11).getTime());
        info.setEmail("aabb@ccdd.com");
        Student s = new Student();
        s.setName("Wang");
        s.setInfo(info);
        session.save(s);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
    
    /**
     * 修改info关系
     * */
    @Test
    public void testUpdate(){
        Transaction transaction = session.beginTransaction();
        Student s = session.get(Student.class, 1l);
        StudentInfo info = new StudentInfo();
        info.setAddress("Address123");
        info.setBirthday( new GregorianCalendar(2015, 2, 11).getTime());
        info.setEmail("aabb111@ccdd.com");
        s.setInfo(info);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

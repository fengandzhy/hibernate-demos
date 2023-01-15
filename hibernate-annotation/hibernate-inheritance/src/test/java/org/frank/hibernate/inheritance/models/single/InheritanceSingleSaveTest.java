package org.frank.hibernate.inheritance.models.single;

import org.frank.hibernate.inheritance.models.InheritanceTest;
import org.frank.hibernate.inheritance.models.joined.AutomationWebAppMenu;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.GregorianCalendar;

public class InheritanceSingleSaveTest extends InheritanceTest {
    
    @Test
    public void testSaveWindowFile(){
        Transaction transaction = session.beginTransaction();
        WindowFile windowFile = new WindowFile();
        windowFile.setDate(new GregorianCalendar(2012,0,3).getTime());
        windowFile.setName("windowFile1");
        windowFile.setType("A");
        session.save(windowFile);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }        
    }

    @Test
    public void testSaveDocument(){
        Transaction transaction = session.beginTransaction();
        Document document = new Document();
        document.setDate(new GregorianCalendar(2012,2,3).getTime());
        document.setName("Document1");
        document.setType("B");
        document.setSize("10KB");
        session.save(document);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    @Test
    public void testSaveFolder(){
        Transaction transaction = session.beginTransaction();
        Folder folder = new Folder();
        folder.setDate(new GregorianCalendar(2012,4,3).getTime());
        folder.setName("Folder1");
        folder.setType("B");
        folder.setFileCount(3);
        session.save(folder);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
    
    @Test
    public void saveAutoMenu(){
        Transaction transaction = session.beginTransaction();
        AutomationWebAppMenu menu = new AutomationWebAppMenu();
        menu.setFoot("foot");
        menu.setAbstract(false);
        menu.setDescription("Auto menu");
        menu.setName("name");
        menu.setPinyin("pin yin");
        menu.setRemark("remark");
        menu.setShow(true);
        menu.setStatus(true);
        session.save(menu);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

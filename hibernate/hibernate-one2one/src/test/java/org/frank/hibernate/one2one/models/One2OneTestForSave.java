package org.frank.hibernate.one2one.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class One2OneTestForSave extends One2OneTest{
    
    /**
     * 1 一对一这里没有inverse=true 属性配置, 这里是默认的是person.setCard(card); 管理外键.
     * 
     * 2. 首先外键建在t_person 表里, 所以在person 的配置文件中是 many-to-one unique=true
     * 
     * 3. 在card里面是 one-to-one 是不负责维护外键的 也就是说 card.setPerson(person); 并不影响外键.
     * 
     * */
    @Test
    public void testSave(){
        Transaction transaction = session.beginTransaction();
        Person person = new Person();
        Card card = new Card("1");
        person.setName("A1");
        person.setCard(card);
        card.setPerson(person);
        
        session.save(person);
        session.save(card);        

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

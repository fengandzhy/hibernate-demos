package org.frank.hibernate.one2one.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class One2OneTestForSave extends One2OneTest{
    
    /**
     * 1. 这里person表里面有外键cid，所以是Person类来维护外键, person.setCard(card); 维护外键, 所以在Person 类中必须配置@JoinColumn 
     * 
     * 2. 由于card表不维护外键 所以Card 里必须是mappedBy
     * 
     * 
     * */
    @Test
    public void testSave(){
        Transaction transaction = session.beginTransaction();
        Person person = new Person();
        Card card = new Card("1");
        person.setName("A1");
        person.setCard(card);
        // card.setPerson(person);
        
        session.save(person);
        session.save(card);        

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

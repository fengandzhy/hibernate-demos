package org.zhouhy.hibernate.many2one.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class Many2OneTestForInverse extends Many2OneTest{

    @Test
    public void testSave1(){
        Transaction transaction = session.beginTransaction();
        Author author = new Author();
        author.setName("A");

        Article a1 = new Article();
        a1.setName("a1");
        a1.setAuthor(author);

        Article a2 = new Article();
        a2.setName("a2");
        a2.setAuthor(author);

        
        session.save(a1);
        session.save(a2);
        session.save(author);

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

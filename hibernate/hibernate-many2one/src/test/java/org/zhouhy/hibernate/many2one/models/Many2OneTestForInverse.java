package org.zhouhy.hibernate.many2one.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class Many2OneTestForInverse extends Many2OneTest{

    
    /**
     *    单项多对一关联，     
     *         <many-to-one name="author" class="Author">
     *              <column name="author_id"/>
     *         </many-to-one>
     *   先保存Author端再保存Article端执行三条insert语句, 
     *   先保存Article端在保存Author端执行三条insert语句, 外交两条update语句.
     * 
     * */
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

        session.save(author);
        session.save(a1);
        session.save(a2);        

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

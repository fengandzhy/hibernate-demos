package org.zhouhy.hibernate.many2one.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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

    @Test
    public void testSave2(){
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
    
    /**
     * @OneToMany(fetch = FetchType.EAGER,mappedBy = "author") 
     * 表示inverse=true 也就是说 author.setArticles(articles); 不参与外键维护
     * 
     * @OneToMany(fetch = FetchType.EAGER)
     * @JoinColumn(name="author_id")
     * 表示 inverse=false author.setArticles(articles); 要参与外键维护
     * */
    @Test
    public void testSave3(){
        Transaction transaction = session.beginTransaction();
        Author author = new Author();
        author.setName("A");

        Article a1 = new Article();
        a1.setName("a1");
        a1.setAuthor(author);

        Article a2 = new Article();
        a2.setName("a2");
        a2.setAuthor(author);

        Set<Article> articles = new HashSet<>();
        articles.add(a1);
        articles.add(a2);

        author.setArticles(articles);

        session.save(author);
        session.save(a1);
        session.save(a2);

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

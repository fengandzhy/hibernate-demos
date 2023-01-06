package org.zhouhy.hibernate.many2one.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class Many2OneTestForInverse extends Many2OneTest{

    
    /**
     *    单项多对一关联，     
     *         <many-to-one name="author" class="Author">
     *              <column name="author_id"/>
     *         </many-to-one>
     *   先保存Author端再保存Article端执行三条insert语句, 
     *   先保存Article端在保存Author端执行三条insert语句, 外交两条update语句. 因为先保存Article时，Author还没有保存, 当Author 执行update语句更新外键
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

    /**
     *   在Article 端 没有办法实现 inverse="true" 所以 a1.setAuthor(author); a2.setAuthor(author); 必须参与外键维护. 
     *
     * */

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
     *   在Author 端 inverse="true" 时主要表示  author.setArticles(articles); 不会参与外键维护     
     *         <set name="articles" table="t_article" inverse="true">
     *             <key>
     *                 <column name="author_id"/>
     *             </key>
     *             <one-to-many class="Article"/>
     *         </set>
     *  当inverse = true 先保存author 再保存 article时 执行三条insert 
     *  当没有inverse = true 时， 先保存author 再保存 article时 执行三条insert 之后再来两条update 因为author.setArticles(articles); 参与了外键维护
     *  
     *  当inverse = true 先保存article 再保存 author时 执行三条insert 之后再来两条update 
     *  因为 a1 a2 存在数据库里是为空, 而a1.setAuthor(author); a2.setAuthor(author);需要维护外键
     *  
     *  当没有inverse = true 时 先保存article 再保存 author时 执行三条insert 之后再来四条update 因为两条是给 a1.setAuthor(author); a2.setAuthor(author);需要维护外键
     *  另外两条是 author.setArticles(articles); 参与了外键维护
     * 
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

        
        session.save(a1);
        session.save(a2);
        session.save(author);

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

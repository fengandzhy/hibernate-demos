package org.zhouhy.hibernate.many2one.models;


import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class Many2OneTestForCascade extends Many2OneTest{

    /**
     * 1 当Author端设置 cascade="save-update"时, 如果Author是一个临时对象, Article 也是临时对象
     * 保存Author对象时也会把Article对象保存进去.
     *
     * 2 这里用save 和 persist 的结果是一样的
     * 
     * 3 另外在Author端设置  cascade="save-update" 只有当设置author.setArticles(articles); 才会涉及 articles的级联操作.
     * 级联操作的意思就是说, 当我们保存author 时，也会一并保存与之相连的对象 a1 和 a2， session.persist(author);
     * 就是说不需要手动地再去 session.persist(a1); session.persist(a2);
     * 
     * */
    @Test
    public void testCascadeSaveUpdateForTransientObj(){
        Transaction transaction = session.beginTransaction();
        Author author = new Author();
        author.setName("A");

        Article a1 = new Article();
        a1.setName("a1");
        a1.setAuthor(author);

        Set<Article> articles = new HashSet<>();
        articles.add(a1);
        author.setArticles(articles);

        session.persist(author);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

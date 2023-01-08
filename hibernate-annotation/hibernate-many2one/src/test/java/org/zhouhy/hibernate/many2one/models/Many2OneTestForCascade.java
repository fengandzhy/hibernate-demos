package org.zhouhy.hibernate.many2one.models;


import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class Many2OneTestForCascade extends Many2OneTest{

    /**
     * 1 当Author端设置 cascade="save-update"时, 如果Author是一个临时对象, Article 也是临时对象
     * 保存Author对象时也会把Article对象保存进去.
     *
     * 2 这里用save 和 persist 的结果是一样的, 它会执行两条insert语句, 一条是在 执行 persist/save的时候, 一条是在执行 commit 的时候
     * 因为当cascade=save-update 时, 当author 已经因为执行了persist/save 成了持久对象, 它会把author关联的对象也给弄成 持久状态的. 
     *
     * 3 另外在Author端设置  @Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE) 只有当设置author.setArticles(articles); 才会涉及 articles的级联操作.
     * 级联操作的意思就是说, 当我们保存author 时，也会一并保存与之相连的对象 a1 和 a2， session.persist(author);
     * 就是说不需要手动地再去 session.persist(a1); session.persist(a2);
     *
     * */
    @Test
    public void testCascadeSaveUpdateForTransientAuthor(){
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

    /**
     *   1.正常情况下讲 cascade=none时,  下面的代码不会造成任何改变, 因为author是持久对象, a1是临时对象 而当 cascade=none 当执行commit的时候, 不会级联保存 a1,
     *    这个 author.setArticles(articles); 只是改变了author的关联, 由于在author端 inverse=true, 所以它不负责改变外键. 所以当cascade=none时它不会改变任何值.
     *    但是如果把这个 inverse=false的时候，麻烦就来了，由于 a1是任然是临时对象, 而author.setArticles(articles); 让author关联到了这个临时对象, 而commit的时候
     *    临时对象还是临时对象, 所以就会爆出 TransientObjectException. 
     *
     *   2. 当 @Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)时, 因为author是持久对象, a1是临时对象, 当执行commit的时候 会级联保存a1, 这个 author.setArticles(articles); 只是改变了author的关联, 
     *   由于在author端 inverse=true, 所以它不负责改变外键. 所以当cascade=save-update 时它只是级联增加了一条记录而不会改变原有记录的外键值. 
     *   但是如果把这个 inverse=false的时候，它会改变之前跟author关联的对象的外键值. 
     *
     * */
    @Test
    public void testCascadeSaveUpdateForPersistentAuthor(){
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class,27L);

        Article a1 = new Article();
        a1.setName("a1");
        a1.setAuthor(author);

        Set<Article> articles = new HashSet<>();
        articles.add(a1);
        author.setArticles(articles);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 1.当 cascade="none" author为游离对象, 而 a1为临时对象, 因此当 session.update(author);  把author变成持久化对象时, 不会去级联保存 a1. 
     * 2.当 cascade="save-update" author为游离对象, 而 a1为临时对象, 因此当 session.update(author);  把author变成持久化对象时, 会去级联保存 a1. 
     * 3. 即便cascade="save-update" 如果没有 session.update(author);  把author变成持久化对象, 也不会去级联保存a1. 
     *
     *
     * */
    @Test
    public void testCascadeSaveUpdateForDetachedAuthor1(){
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class,27L);
        session.evict(author);
        Article a1 = new Article();
        a1.setName("a1");
        a1.setAuthor(author);

        Set<Article> articles = author.getArticles(); // 由于author已经是游离对象了,如果是懒加载，这里就会报错,所以一定是立即加载或者fetch = join

        articles.add(a1);
        author.setArticles(articles);

        session.update(author);

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 1.当 cascade="save-update" 即便是session.evict(author);把author变成游离对象时, 里面的 article 依然是持久对象
     * 2. 即便cascade="evict" session.evict(author);把author变成游离对象时 与之关联的 article 也是游离对象. 除非在 session.evict(author); 改变关联关系.
     *
     * */
    @Test
    public void testCascadeSaveUpdateForDetachedAuthor2(){
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class,27L);
        session.evict(author);
        Set<Article> articles = author.getArticles();
        for(Article article:articles){
            assertTrue(session.contains(article));
        }
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * cascade=CascadeType.PERSIST 用persist 方法保存一个关联着游离对象的临时对象会报错. 
     * 用save 方法就不会报错, 但不会把游离对象级联变成持久化对象, 就是说游离对象还是无法改变. 
     * 
     * */
    @Test
    public void testCascadePersistForTransientAuthor2(){
        Transaction transaction = session.beginTransaction();
        Author author = new Author();
        author.setName("A");

        Article a1 = session.get(Article.class,41L);
        session.evict(a1);

        Set<Article> articles = author.getArticles();
        articles.add(a1);
        author.setArticles(articles);

//        session.save(author);
        session.persist(author);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
    
//    public void testCascadePersistForDetachedAuthor(){
//        Transaction transaction = session.beginTransaction();
//        
//        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
//            transaction.commit();
//        }
//    }
}

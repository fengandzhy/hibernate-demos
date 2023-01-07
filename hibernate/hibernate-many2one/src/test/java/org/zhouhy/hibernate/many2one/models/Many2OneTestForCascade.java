package org.zhouhy.hibernate.many2one.models;


import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;
import javax.persistence.PersistenceException;
import java.util.HashSet;
import java.util.Iterator;
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
     * 3 另外在Author端设置  cascade="save-update" 只有当设置author.setArticles(articles); 才会涉及 articles的级联操作.
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
     *  1. 正常情况下讲 cascade=none时, author为持久化对象, 修改持久化对象的某个属性当commit时,执行一条update语句, 会更改相应的记录
     *  
     * */
    @Test
    public void testUpdateMany2OneOjb1(){
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class,27L);
        assertTrue(session.contains(author));
        author.setName("AB");        
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     *  1. 正常情况下讲 cascade=none时, author为持久化对象,  article 也吃持久对象, 修改article的某个属性,当commit时,执行一条update语句, 会更改相应的记录
     *
     * */
    @Test
    public void testUpdateMany2OneOjb2(){
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class,27L);
        Set<Article> articles = author.getArticles();
        for(Article article:articles){
            assertTrue(session.contains(article));
            article.setName("CD");
        }
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     *  1. 正常情况下讲 cascade=none时, author为持久化对象,  article 也吃持久对象, 但是执行了 iterator.remove(); 之后article仍然是持久化对象, 所以它并不删除article  
     *  2 iterator.remove(); 只是解除了又author 到 article 的关联关系, 由于Author 端 inverse= true 所以单纯地解除 author 到 article 的关联关系, 并没有改变外键值
     *  
     * */
    @Test
    public void testUpdateMany2OneOjb3(){
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class,27L);
        Set<Article> articles = author.getArticles();
        Iterator<Article> iterator = articles.iterator();
        while(iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 1. 正常情况下讲 cascade=none时, author为持久化对象,  article 也吃持久对象, 所以当 session.delete(a); 时 article变成临时对象, 所以当commit时article在数据库中就会删除.
     * 2. 但是当 cascade=save-update时, 这个就有很大的问题了, 由于author为持久化对象, 当commit 的时候, 它会级联到它所关联的对象, 就是 article, 发现此时的article 由于session.delete(a);
     * 变成了临时对象, 所以它会再次地把它变成持久化对象, 因此, 这样的话跟之前的delete操作矛盾，所以就会抛出异常. 所以要加上 iterator.remove(); 来解除 author 到 article 的关联关系.
     * 
     * 
     * */
    @Test
    public void testUpdateMany2OneOjb4(){
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class,27L);
        Set<Article> articles = author.getArticles();
        Iterator<Article> iterator = articles.iterator();
        while(iterator.hasNext()){
            Article a = iterator.next();
            session.delete(a);
            iterator.remove(); // 当 cascade=save-update时 这里一定要加上这句来解除author 到 article的关联关系, 否则就要报错.
        }        
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
     *   2. 当 cascade=save-update时, 因为author是持久对象, a1是临时对象, 当执行commit的时候 会级联保存a1, 这个 author.setArticles(articles); 只是改变了author的关联, 
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

        Set<Article> articles = author.getArticles();       
        
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
     * 1 当cascade="persist" author与a1 都是临时对象时，author因为 执行 session.persist(author); 变成持久对象后，它也会级联到与之关联的 a1对象.
     * 
     * */
    @Test
    public void testCascadePersistForTransientAuthor1(){
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
     * 1 当cascade="persist" author临时对象时，a1是游离对象，author因为 执行 session.persist(author); 变成持久对象后，commit的时候会报错, 因为cascade="persist" 被级联的
     * 不能是游离对象.
     * 2 当cascade="save-update" author临时对象时，a1是游离对象，author因为 执行 session.persist(author); 变成持久对象后，可以级联更新 a1
     *
     * */
    @Test
    public void testCascadePersistForTransientAuthor2(){
        Transaction transaction = session.beginTransaction();
        Author author = new Author();
        author.setName("A");

        Article a1 = new Article();
        a1.setId(66);
        a1.setName("a2");
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
     * 1 当cascade="persist" author为持久化对象 a1是临时对象, 它不会级联a1 
     * 
     * */
    @Test
    public void testCascadePersistForPersistentAuthor(){
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class,27L);

        Article a1 = new Article();
        a1.setName("a1");
        a1.setAuthor(author);

        Set<Article> articles = author.getArticles();
        articles.add(a1);
        author.setArticles(articles);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     *  1 当cascade="persist" author为游离对象 a1是临时对象, session.update(author); author 变成了持久化对象,它不会级联a1     
     * */
    @Test
    public void testCascadePersistForDetachedAuthor(){
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class,27L);
        session.evict(author);
        Article a1 = new Article();
        a1.setName("a1");
        a1.setAuthor(author);

        Set<Article> articles = author.getArticles();        

        articles.add(a1);
        author.setArticles(articles);

        session.update(author);

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

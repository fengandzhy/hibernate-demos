package org.zhouhy.hibernate.many2one.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Many2OneTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionFactory sessionFactory = null;
    private Session session = null;
    private SimpleDateFormat sm;

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
        sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
        sm = new SimpleDateFormat("yyyy-MM-dd");
    }
    
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

    @Test
    public void testSave3(){
        Transaction transaction = session.beginTransaction();
        Author author = new Author();
        author.setName("A");

        Article a1 = new Article();
        a1.setName("a1");
//        a1.setAuthor(author);

        Article a2 = new Article();
        a2.setName("a2");
//        a2.setAuthor(author);
        
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

    
    /**
     * 1 如果没有设置cascade的话, 这里没有调用 session.save(a1); session.save(a2); 那么a1,a2 这两条记录是不会插入到 表中的
     * 2 如果在多端(Author端)设置了cascade="save-update", 那么在session.save(author); 
     * 时即便没有调用session.save(a1); session.save(a2); 也会相应的去保存a1 a2
     * 3 另外非常要注意的是, 当在多端(Author端)设置了cascade="save-update" 的时候, 当保存session.save(author); 的时候, 它只是同时会保存
     * author的关联 Article对象.
     * 
     * */
    @Test
    public void testSave4(){
        Transaction transaction = session.beginTransaction();
        Author author = new Author();
        author.setName("A");

        Article a1 = new Article();
        a1.setName("a1");
//        a1.setAuthor(author);

        Article a2 = new Article();
        a2.setName("a2");
//        a2.setAuthor(author);

        Set<Article> articles = new HashSet<>();
        articles.add(a1);
        articles.add(a2);

        author.setArticles(articles);

        session.save(author);        

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 1 由于在多端(Article端)设置了cascade="save-update", 所以当其保存a1时，它也会保存a1的关联对象 author, 但是由于在Author端
     * 也同样设置了 cascade="save-update" 所以当author被保存的时候，它也会保存它关联的对象 a2. 
     * 
     * 2 cascade究竟是什么意思呢？
     * 
     * */
    @Test
    public void testSave5(){
        Transaction transaction = session.beginTransaction();
        Author author = new Author();
        author.setName("A");

        Article a1 = new Article();
        a1.setName("a1");
        a1.setAuthor(author);

        Article a2 = new Article();
        a2.setName("a2");

        Set<Article> articles = new HashSet<>();
        articles.add(a1);
        articles.add(a2);

        author.setArticles(articles);

        session.save(a1);

        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
    
    /**
     * 1 注意当延迟加载时一定是getName()方法才会触发SQL语句, getId()方法都不行，因为Author本身的Id值就作为外键存储在Article对应的表中
     * 所以在延迟加载的时候getId()并不能触发对应的SQL语句. 
     * 2 当多端(Article端)配置了lazy="false"的时候, 查询这个多端(Article端)时, 会立即带出一端(Author端)的数据
     * 3 当多端(Article端)不配置了lazy="false"的时候(注意多端不能写lazy="ture"), 查询这个多端(Article端)时, 会延迟带出一端(Author端)的数据
     * 4 当多端(Article端)不配置了fetch="join"的时候,查询这个多端(Article端)时,会用这个join的方式来找到一端(Author端)
     * */
    @Test
    public void testGet1(){
        Article a1 = session.get(Article.class,1L);       
    }

    /**     
     * 1 当一端(Author端)配置了lazy="false"的时候, 查询这个一端(Author端)时, 会立即带出多端(Article端)的数据
     * 2 以此类推可以得到上述相似的其他结论     
     * */
    @Test
    public void testGet2(){
        Author author = session.get(Author.class,1L);
    }

    /**
     * 1 当Author端设置 cascade="save-update"时, 如果Author是一个临时对象, Article 也是临时对象
     * 保存Author对象时也会把Article对象保存进去.
     * 
     * 2 这里用save 和 persist 的结果是一样的
     * */
    @Test
    public void testCascade1(){
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
     * 1 当Author时持久状态对象时, Article是临时对象, 当author对象更新时，它会级联更新与之相关联的临时对象.
     * 
     * 2 注意这里一定要加上transaction的begin和commit 否则修改无效
     * */
    @Test
    public void testCascade2(){
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
     * 1 当Author为游离对象时, Article也为游离对象, 此时当Author对象变成持久化对象时， 它会级联更新与之相关联的对象
     * 
     * 总结, 无论Author是临时对象,持久对象还是游离对象, 当它变成持久对象时，如果此时cascade="save-update" 它都会级联的更新与之相关联的对象
     * 
     * 什么叫级联更新？就是有关联关系的时候会去更新, 现在的cascade="save-update"设置在Author端, 所以级联关系是author.setArticles(articles); 
     * */
    @Test
    public void testCascade3(){
        Transaction transaction = session.beginTransaction();
        Author author = new Author();
        author.setName("B");
        author.setId(27L);
        Article a1 = new Article();
        a1.setId(27L); 
        a1.setName("a3");
        a1.setAuthor(author);
        Set<Article> articles = new HashSet<>();
        articles.add(a1);
        author.setArticles(articles);
        session.update(author);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }   

    @After
    public void destroy() {
        session.close();
        sessionFactory.close();
    }
}

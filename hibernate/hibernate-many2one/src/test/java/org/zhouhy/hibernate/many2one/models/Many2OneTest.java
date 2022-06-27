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
import java.util.HashSet;
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

    @After
    public void destroy() {
        session.close();
        sessionFactory.close();
    }
}

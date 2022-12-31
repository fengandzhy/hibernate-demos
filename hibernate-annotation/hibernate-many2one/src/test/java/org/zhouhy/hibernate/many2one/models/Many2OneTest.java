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
    protected Session session = null;
    

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
        sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();        
    }
    
//    @Test
//    public void testSave1(){
//        Transaction transaction = session.beginTransaction();
//        Author author = new Author();
//        author.setName("A");
//        
//        Article a1 = new Article();
//        a1.setName("a1");
//        a1.setAuthor(author);
//        
//        Article a2 = new Article();
//        a2.setName("a2");
//        a2.setAuthor(author);
//        
//        session.save(author);
//        session.save(a1);
//        session.save(a2);
//
//        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
//            transaction.commit();
//        }
//    }
//

//
//    /**
//     * 1 注意@ManyToOne的默认是立即加载, 这一点跟映射文件 many-to-one有些不同
//     * */
//    @Test
//    public void testGet1(){
//        Article a1 = session.get(Article.class,13L);
//    }
//
//    @Test
//    public void testGet2(){
//        Author author = session.get(Author.class,1L);
//    }
//
//    @Test
//    public void testGet3(){
//        Author author = session.get(Author.class,1L);
//    }
//
//    /**
//     * 1 当 CascadeType.PERSIST 时只有persist方法才能级联保存 save 方法不行.
//     * */
//    @Test
//    public void testCascade1(){
//        Transaction transaction = session.beginTransaction();
//        Author author = new Author();
//        author.setName("A");
//
//        Article a1 = new Article();
//        a1.setName("a1");
//        a1.setAuthor(author);
//
//        Set<Article> articles = new HashSet<>();
//        articles.add(a1);
//        author.setArticles(articles);
//
//        session.save(author);
//        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
//            transaction.commit();
//        }
//    }

    @After
    public void destroy() {
        session.close();
        sessionFactory.close();
    }
}

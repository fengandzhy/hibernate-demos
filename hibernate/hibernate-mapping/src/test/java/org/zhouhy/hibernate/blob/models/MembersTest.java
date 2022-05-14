package org.zhouhy.hibernate.blob.models;


import org.hibernate.Hibernate;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class MembersTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionFactory sessionFactory = null;
    private Session session = null;
    private Transaction transaction = null;
    private SimpleDateFormat sm;

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
        sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
        sm = new SimpleDateFormat("yyyy-mm-dd");
    }

    @Test
    public void saveTest() throws ParseException, IOException {
        transaction = session.beginTransaction();
        Member member = new Member();
        member.setName("F");
        member.setTextContent("QY");
        member.setSex("M");
        member.setBirthday(sm.parse("2000-12-23"));
        member.setMyBlob(this.populateBlob(session));
        session.save(member);
        System.out.println(transaction.getStatus());
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }       
    }

    /**
     * 这里的get方法不需要Member显示的指定构造器, 就是说session.get(Member.class,1L); 可以把对应的member 取到, 不需要Member非得有一个包含
     * 全部属性的构造器
     * */
    @Test
    public void getTest() {
        Member member = session.get(Member.class,1L);
        logger.info(member.toString());
    }

    @After
    public void destroy() {
        session.close();
        sessionFactory.close();
    }
    
    private Blob populateBlob(Session session) throws IOException {
        InputStream inputStream=new FileInputStream("/img/720.jpg");
        byte[] byteArray2=new byte[inputStream.available()];
        inputStream.read(byteArray2);
        inputStream.close();
        return Hibernate.getLobCreator(session).createBlob(byteArray2);
    }
}

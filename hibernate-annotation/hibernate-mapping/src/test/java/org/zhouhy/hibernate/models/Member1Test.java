package org.zhouhy.hibernate.models;

import com.sun.istack.NotNull;
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

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Member1Test {

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
    public void saveTest() throws ParseException, IOException {
        Transaction transaction = session.beginTransaction();
        Member1 member = new Member1();
        member.setName("F");
        member.setTextContent("QY");
        member.setSex("M");
        member.setBirthday(sm.parse("2000-12-23"));
        member.setFileImage(this.getBytes());
        session.save(member);
        System.out.println(transaction.getStatus());
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    @Test
    public void getTest() throws SQLException, IOException {
        Member1 member = session.get(Member1.class,1L);
        this.writeBlob(member.getFileImage());
    }

    @After
    public void destroy() {
        session.close();
        sessionFactory.close();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private byte[] getBytes() throws IOException {
        InputStream inputStream=this.getClass().getResourceAsStream("/img/720.jpg");
        assert inputStream != null;
        byte[] byteArray2=new byte[inputStream.available()];
        inputStream.read(byteArray2);
        inputStream.close();
        return byteArray2;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void writeBlob(@NotNull byte[] bytes) throws SQLException, IOException {        
        File file = new File("D://test//c.jpg");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        InputStream is = new ByteArrayInputStream(bytes);
        byte[] buff = new byte[1024];
        int len;
        while((len=is.read(buff))!=-1){
            fos.write(buff, 0, len);
        }
        is.close();
        fos.close();
        logger.info("write blob finished!");
    }
}

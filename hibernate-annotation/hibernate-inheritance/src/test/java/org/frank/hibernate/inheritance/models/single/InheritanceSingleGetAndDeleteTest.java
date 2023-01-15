package org.frank.hibernate.inheritance.models.single;

import org.frank.hibernate.inheritance.models.InheritanceTest;
import org.junit.Test;

public class InheritanceSingleGetAndDeleteTest extends InheritanceTest {
    
    @Test
    public void getWindowFile(){
        WindowFile windowFile = session.get(WindowFile.class,1);
        logger.info(windowFile.toString());
    }

    @Test
    public void getFolder(){
        WindowFile windowFile = session.get(WindowFile.class,4);
        logger.info(windowFile.getClass().toString());
    }

    @Test
    public void getDocument(){
        WindowFile windowFile = session.get(WindowFile.class,3);
        logger.info(windowFile.getClass().toString());
    }
}

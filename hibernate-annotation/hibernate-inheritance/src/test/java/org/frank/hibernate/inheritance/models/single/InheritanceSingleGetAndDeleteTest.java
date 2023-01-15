package org.frank.hibernate.inheritance.models.single;

import org.frank.hibernate.inheritance.models.InheritanceTest;
import org.junit.Test;

public class InheritanceSingleGetAndDeleteTest extends InheritanceTest {
    
    @Test
    public void testGetWindowFile(){
        WindowFile windowFile = session.get(WindowFile.class,1);
        logger.info(windowFile.toString());
    }

    @Test
    public void testGetFolder(){
        WindowFile windowFile = session.get(WindowFile.class,4);
        logger.info(windowFile.getClass().toString());
    }

    @Test
    public void testGetDocument(){
        WindowFile windowFile = session.get(WindowFile.class,3);
        logger.info(windowFile.getClass().toString());
    }
}

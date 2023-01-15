package org.frank.hibernate.inheritance.models.single;

import org.frank.hibernate.inheritance.models.InheritanceTest;
import org.junit.Test;

import java.util.GregorianCalendar;

public class InheritanceSingleSaveTest extends InheritanceTest {
    
    @Test
    public void testWindowFile(){
        WindowFile windowFile = new WindowFile();
        windowFile.setDate(new GregorianCalendar(2012,12,3).getTime());
        windowFile.setName("windowFile1");
        
    }
}

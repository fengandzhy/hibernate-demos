package org.frank.hibernate.inheritance.models.joined;


import org.frank.hibernate.inheritance.models.InheritanceTest;
import org.junit.Test;

public class InheritanceJoinedGetTest extends InheritanceTest {

    @Test
    public void testGetAnimal(){
        Animal animal = session.get(Animal.class,1);
        logger.info(animal.getClass().toString());
    }

    @Test
    public void testGetBird(){
        Animal animal = session.get(Animal.class,2);
        logger.info(animal.getClass().toString());
    }

    @Test
    public void testGetDog(){
        Animal animal = session.get(Animal.class,3);
        logger.info(animal.getClass().toString());
    }
}

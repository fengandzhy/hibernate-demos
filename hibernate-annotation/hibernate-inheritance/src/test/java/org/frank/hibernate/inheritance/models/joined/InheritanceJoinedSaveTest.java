package org.frank.hibernate.inheritance.models.joined;

import org.frank.hibernate.inheritance.models.InheritanceTest;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class InheritanceJoinedSaveTest extends InheritanceTest {
    
    @Test
    public void testSaveAnimal(){
        Transaction transaction = session.beginTransaction();
        Animal animal = new Animal();
        animal.setColor("Red");
        animal.setName("Animal");
        session.save(animal);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    @Test
    public void testSaveBird(){
        Transaction transaction = session.beginTransaction();
        Bird bird = new Bird();
        bird.setColor("Blue");
        bird.setName("Bird");
        bird.setSpeed("100KM/s");
        session.save(bird);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    @Test
    public void testSaveDog(){
        Transaction transaction = session.beginTransaction();
        Dog dog = new Dog();
        dog.setColor("Green");
        dog.setName("Dog");
        dog.setLegs(4);
        session.save(dog);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

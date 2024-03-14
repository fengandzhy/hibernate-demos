package org.frank.hibernate.inheritance.models.union;

import org.frank.hibernate.inheritance.models.InheritanceTest;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class InheritanceUnionSaveTest extends InheritanceTest {
    
    @Test
    public void testSaveCar(){
        Transaction transaction = session.beginTransaction();
        Car car = new Car();
        car.setSpeed(200);
        car.setEngine("En");
        session.save(car);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    @Test
    public void testSaveBike(){
        Transaction transaction = session.beginTransaction();
        Bike bike = new Bike();
        bike.setSpeed(20);
        bike.setName("bike");
        session.save(bike);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    @Test
    public void testSaveVehicle(){
        Transaction transaction = session.beginTransaction();
        Vehicle vehicle = new Vehicle();
        vehicle.setSpeed(100);
        session.save(vehicle);
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}

package org.frank.hibernate.inheritance.models.union;

import org.frank.hibernate.inheritance.models.InheritanceTest;
import org.junit.Test;

public class InheritanceUnionGetTest extends InheritanceTest {
    
    @Test
    public void testGetForUnion(){
        Vehicle vehicle = session.get(Vehicle.class, "629a4d1c-a325-4962-9055-0c0c65c37392");
        logger.info(vehicle.getClass().toString());
    }
}

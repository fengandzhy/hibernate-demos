package org.zhouhy.hibernate.many2one.models;

import org.junit.Test;

import java.util.Arrays;

public class Many2OneTestForCascadePersonAndAddress extends Many2OneTest{
    
    @Test
    public void whenParentSavedThenChildSaved() {
        Person person = new Person();
        Address address = new Address();
        address.setPerson(person);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.flush();
        session.clear();
    }
}

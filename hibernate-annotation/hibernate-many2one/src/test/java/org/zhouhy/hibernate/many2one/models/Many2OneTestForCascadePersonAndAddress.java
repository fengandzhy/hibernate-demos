package org.zhouhy.hibernate.many2one.models;

import com.sun.istack.NotNull;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.Arrays;

public class Many2OneTestForCascadePersonAndAddress extends Many2OneTest{
    
    /**
     * CascadeType.PERSIST 
     * The persist operation makes a transient instance persistent. 
     * Cascade Type PERSIST propagates the persist operation from a parent to a child entity. 
     * When we save the person entity, the address entity will also get saved.
     * */
    @Test
    public void whenParentSavedThenChildSaved() {
        Transaction transaction = session.beginTransaction();
        Person person = new Person();
        Address address = new Address();
        address.setPerson(person);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.flush();
        session.clear();
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    @Test
    public void whenParentSavedThenMerged() {
        int addressId;
        Person person = buildPerson("devender");
        Address address = buildAddress(person);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.flush();
        addressId = address.getId();
        session.clear();

        Address savedAddressEntity = session.find(Address.class, addressId);
        Person savedPersonEntity = savedAddressEntity.getPerson();
        savedPersonEntity.setName("devender kumar");
        savedAddressEntity.setHouseNumber(24);
        session.merge(savedPersonEntity);
        session.flush();
    }
    
    private Person buildPerson(@NotNull String name){
        Person person = new Person();
        person.setName(name);
        return person;
    }
    
    private Address buildAddress(@NotNull Person person){
        Address address = new Address();
        address.setPerson(person);
        return address;
    }
}

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

    /**
     * CascadeType.MERGE
     * The merge operation copies the state of the given object onto the persistent object with the same identifier. 
     * CascadeType.MERGE propagates the merge operation from a parent to a child entity.
     * */
    @Test
    public void whenParentSavedThenMerged() {
        Transaction transaction = session.beginTransaction();
        int addressId;
        Person person = buildPerson("devender");
        Address address = buildAddress(person);
        person.setAddresses(Arrays.asList(address));
        session.persist(person); // 这里保存person并不伴随着保存address, 因为此时的CascadeType.MERGE
        session.flush();
        addressId = address.getId();
        session.clear();

        Address savedAddressEntity = session.find(Address.class, addressId);
        Person savedPersonEntity = savedAddressEntity.getPerson();
        savedPersonEntity.setName("devender kumar");
        savedAddressEntity.setHouseNumber(24);
        session.merge(savedPersonEntity);
        session.flush();
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
    
    private Person buildPerson(@NotNull String name){
        Person person = new Person();
        person.setName(name);
        return person;
    }
    
    private Address buildAddress(@NotNull Person person){
        Address address = new Address();
        address.setId(6);
        address.setPerson(person);
        return address;
    }
}

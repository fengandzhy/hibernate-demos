package org.zhouhy.hibernate.many2one.models;

import com.sun.istack.NotNull;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;


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
        Address address = buildAddress(person,6);
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

    /**
     * CascadeType.REMOVE
     * As the name suggests, the remove operation removes the row corresponding to the entity from the database and also from the persistent context.
     * 
     * CascadeType.REMOVE propagates the remove operation from parent to child entity. 
     * Similar to JPA's CascadeType.REMOVE, we have CascadeType.DELETE, which is specific to Hibernate. 
     * There is no difference between the two.
     * 
     * */
    @Test
    public void whenParentRemovedThenChildRemoved() {
        Transaction transaction = session.beginTransaction();
        int personId;
        Person person = buildPerson("devender");
        Address address = buildAddress(person,0);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.persist(address);
        session.flush();
        personId = person.getId();
        session.clear();

        Person savedPersonEntity = session.find(Person.class, personId);
        session.remove(savedPersonEntity);
        session.flush();
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
    
    /**
     * CascadeType.DETACH
     * The detach operation removes the entity from the persistent context. 
     * When we use CascadeType.DETACH, the child entity will also get removed from the persistent context.
     * */
    @Test
    public void whenParentDetachedThenChildDetached() {
        Transaction transaction = session.beginTransaction();
        Person person = buildPerson("devender");
        Address address = buildAddress(person,0);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.persist(address);
        session.flush();

        assertTrue(session.contains(person));
        assertTrue(session.contains(address));

        session.detach(person); // CascadeType.DETACH 可以把person的关联对象也从持久化变成游离态
        assertFalse(session.contains(person));
        assertFalse(session.contains(address));
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
    
    private Person buildPerson(@NotNull String name){
        Person person = new Person();
        person.setName(name);
        return person;
    }
    
    private Address buildAddress(@NotNull Person person, int Id){
        Address address = new Address();
        if(Id!=0){
            address.setId(Id); 
        }        
        address.setPerson(person);
        return address;
    }
}

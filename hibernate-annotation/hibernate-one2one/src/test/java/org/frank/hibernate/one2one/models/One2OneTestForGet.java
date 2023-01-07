package org.frank.hibernate.one2one.models;

import org.junit.Test;

public class One2OneTestForGet extends One2OneTest{
    
    /**
     * @JoinColumn 默认的情况下 FetchMode.JOIN, 但是FetchType.LAZY 时它的 FetchMode.SELECT
     * 但是如果把FetchMode 改成 FetchMode.SELECT, 它的 FetchType 是 FetchType.EAGER
     * 
     * */
    @Test
    public void testGetPerson(){
        session.get(Person.class,1L);
    }

    /**
     * mappedBy 默认的情况下 FetchMode.JOIN, 但是FetchType.LAZY 时它的 FetchMode.SELECT
     * 但是如果把FetchMode 改成 FetchMode.SELECT, 它的 FetchType 是 FetchType.EAGER
     *
     * */
    @Test
    public void testGetCard(){
        session.get(Card.class,1L);
    }
}

package org.frank.hibernate.one2one.models;

import org.junit.Test;

public class One2OneTestForGet extends One2OneTest{
    
    /**
     * 默认的情况下 many-to-one 是延迟加载, fetch=select, 当改成lazy=false 时 fetch 不变, 
     * 
     * */
    @Test
    public void testGetPerson(){
        session.get(Person.class,1L);
    }

    /**
     * 默认的情况下fetch=join 可以改成fetch=select 但还是立即加载, 无法通过 lazy=true 来改成延迟加载. 
     * 
     * */
    @Test
    public void testGetCard(){
        session.get(Card.class,1L);
    }
}

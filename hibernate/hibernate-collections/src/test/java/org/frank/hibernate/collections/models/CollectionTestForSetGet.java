package org.frank.hibernate.collections.models;

import org.junit.Test;

public class CollectionTestForSetGet extends CollectionsTest{
    
    @Test
    public void testGetForSetOrderBy(){
        SysUser sysUser = session.get(SysUser.class,1L);
        logger.info(sysUser.toString());
    }
}

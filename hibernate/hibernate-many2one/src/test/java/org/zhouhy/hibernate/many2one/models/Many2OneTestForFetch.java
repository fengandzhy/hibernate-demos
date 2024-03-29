package org.zhouhy.hibernate.many2one.models;

import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

public class Many2OneTestForFetch extends Many2OneTest{

    /**
     * 1 注意当延迟加载时一定是getName()方法才会触发SQL语句, getId()方法都不行，因为Author本身的Id值就作为外键存储在Article对应的表中
     * 所以在延迟加载的时候getId()并不能触发对应的SQL语句. 
     * 2 当多端(Article端)配置了lazy="false"的时候, 查询这个多端(Article端)时, 会立即带出一端(Author端)的数据,此时fetch="select"
     * 3 当多端(Article端)配置了lazy="false"的时候(注意多端不能写lazy="ture"), 查询这个多端(Article端)时, 会延迟带出一端(Author端)的数据,此时fetch="select"
     * 4 当多端(Article端)配置了fetch="join"的时候,查询这个多端(Article端)时,会用这个join的方式来找到一端(Author端)
     * 
     * 1.当配置如下所示时, 默认的情况下是延迟加载，默认的fetch=select 
     *          <many-to-one name="author" class="Author">
     *             <column name="author_id"/>
     *          </many-to-one>
     * 延迟加载只有当取其他非ID的属性时,才会去取数据 
     * 
     * 2 当加入lazy="false"时它会立即加载, 此时fetch=select 
     *          <many-to-one name="author" class="Author" lazy="false">
     *             <column name="author_id"/>
     *          </many-to-one>
     *          
     * 3 当fetch="join"时, 无论lazy怎样，都会用表连接的方式立即加载
     *          <many-to-one name="author" class="Author" fetch="join">
     *             <column name="author_id"/>
     *          </many-to-one>
     * 
     * 
     * */
    @Test
    public void testGetArticleForLazyTest(){
        session.get(Article.class,27L);
    }
    
    /**
     *          
     * 如下配置，默认是lazy fetch = select         
     *          <set name="articles" table="t_article"> 
     *             <key>
     *                 <column name="author_id"/>
     *             </key>
     *             <one-to-many class="Article"/>
     *          </set>
     *
     * 如下配置，当它加入lazy="false" 时是立即加载，fetch = select
     *          <set name="articles" table="t_article" lazy="false"> 
     *             <key>
     *                 <column name="author_id"/>
     *             </key>
     *             <one-to-many class="Article"/>
     *          </set>
     *
     * 当如下配置，无论lazy怎样, 只要fetch=join 都会用表连接的方式立即加载
     *          <set name="articles" table="t_article" fetch="join"> 
     *             <key>
     *                 <column name="author_id"/>
     *             </key>
     *             <one-to-many class="Article"/>
     *          </set>         
     * 
     * */
    @Test
    public void testGetAuthorForLazyTest(){
        session.get(Author.class,27L);        
    }
    
    
}

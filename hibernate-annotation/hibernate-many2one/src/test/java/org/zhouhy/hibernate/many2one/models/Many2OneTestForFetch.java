package org.zhouhy.hibernate.many2one.models;

import org.junit.Test;

public class Many2OneTestForFetch extends Many2OneTest{

    /**    
     * 当注解是如下所示是,默认是立即加载, 默认的FetchMode.JOIN 然后 FetchType.EAGER
     * @ManyToOne
     * @JoinColumn(name="author_id")    
     * private Author author;
     * 
     * 当如下注释时，此时为延迟加载, 但是它的FetchMode.SELECT, 
     *     @ManyToOne(fetch = FetchType.LAZY)
     *     @JoinColumn(name="author_id") 
     *     private Author author;
     * 
     * */
    @Test
    public void testGeArticleForLazyTest(){
        session.get(Article.class,25L);
    }

    /**
     * 当如下注解时，默认是延迟加载, 然后FetchMode.SELECT  
     *      @OneToMany
     *      @JoinColumn(name="author_id")
     *      private Set<Article> articles = new HashSet<>();
     *      
     * 当它改成立即加载的时候,  FetchMode.JOIN
     *      @OneToMany(fetch = FetchType.EAGER)
     *      @JoinColumn(name="author_id")
     *      private Set<Article> articles = new HashSet<>();
     * 
     * */
    @Test
    public void testGetAuthorForLazyTest(){
        session.get(Author.class, 21L);

    }
}

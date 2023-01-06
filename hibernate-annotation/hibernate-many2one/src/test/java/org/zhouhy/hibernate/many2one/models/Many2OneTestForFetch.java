package org.zhouhy.hibernate.many2one.models;

import org.junit.Test;

public class Many2OneTestForFetch extends Many2OneTest{

    /**
     * @ManyToOne 默认的FetchMode.JOIN 然后 FetchType.EAGER
     * 当注解是如下所示是, 默认的FetchMode.JOIN 然后 FetchType.EAGER
     * @ManyToOne
     * @JoinColumn(name="author_id")    
     * private Author author;
     * 
     * */
    @Test
    public void testGeArticleForLazyTest(){
        session.get(Article.class,25L);
//        logger.info(a1.getAuthor().getId()+"");
//        logger.info(a1.getAuthor().toString());
    }

    /**
     * @OneToMany 默认的FetchMode.JOIN 然后 FetchType.LAZY  
     * 
     * */
    @Test
    public void testGetAuthorForLazyTest(){
        session.get(Author.class, 21L);
//        logger.info(author.getArticles().toString());
    }
}

package org.zhouhy.querydsl.ch01.components;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class JPAQueryFactoryConfiguration {

    private final EntityManager entityManager;

    public JPAQueryFactoryConfiguration(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public JPAQueryFactory queryFactory(){
        System.out.println("init JPAQueryFactory successfully");
        return new JPAQueryFactory(entityManager);
    }
}

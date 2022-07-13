package org.zhouhy.querydsl.ch01.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJPA<T> extends JpaRepository<T,Long>, 
        JpaSpecificationExecutor<T>, 
        QuerydslPredicateExecutor<T>{
}

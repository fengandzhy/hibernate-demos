package org.zhouhy.querydsl.ch01.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhouhy.querydsl.ch01.entity.QUserBean;
import org.zhouhy.querydsl.ch01.entity.UserBean;
import org.zhouhy.querydsl.ch01.jpa.UserJPA;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserJPA userJPA;

    private final JPAQueryFactory queryFactory;

    public UserController(UserJPA userJPA, JPAQueryFactory queryFactory) {
        this.userJPA = userJPA;
        this.queryFactory = queryFactory;
    }

    @RequestMapping(value = "/queryAll")
    public List<UserBean> queryAll(){
        //使用querydsl查询
        QUserBean _Q_user = QUserBean.userBean;
        //查询并返回结果集
        return queryFactory
                .selectFrom(_Q_user)//查询源
                .orderBy(_Q_user.id.asc())//根据id倒序
                .fetch();//执行查询并获取结果集
    }

    @RequestMapping(value = "/detail_1/{id}")
    public UserBean detail1(@PathVariable("id") Long id ){
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory.selectFrom(qUserBean).where(qUserBean.id.eq(id))
                .fetchOne();
    }

    @RequestMapping(value = "/detail_2/{id}")
    public Optional<UserBean> detail2(@PathVariable("id") Long id ){
        QUserBean qUserBean = QUserBean.userBean;
        return userJPA.findOne(qUserBean.id.eq(id));
    }

    @RequestMapping(value = "/like_name")
    public List<UserBean> queryLike (String name ){
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory.selectFrom(qUserBean)
                .where(qUserBean.name.like(name))
                .fetch();
    }
}
